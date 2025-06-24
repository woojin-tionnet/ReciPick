package com.woojin.recipick.util.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object AESEncrypt {

    private const val ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore"
    private const val KEY_ALIAS = "aes_encryption_key"
    // AES/GCM/NoPadding을 Keystore와 함께 사용하기 위한 표준 알고리즘 문자열
    private const val AES_GCM_NO_PADDING_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES + "/" +
            KeyProperties.BLOCK_MODE_GCM + "/" +
            KeyProperties.ENCRYPTION_PADDING_NONE

    private const val IV_LENGTH_BYTES = 12 // GCM 권장 IV 길이 (96 비트)
    private const val TAG_LENGTH_BIT = 128 // GCM 인증 태그 길이 (128, 120, 112, 104, 96 중 선택)

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE_PROVIDER).apply {
            load(null)
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        if (existingKey != null) {
            return existingKey.secretKey
        }

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE_PROVIDER
        )
        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    fun encrypt(data: String): String? {
        return try {
            val secretKey = getOrCreateSecretKey()
            val cipher = Cipher.getInstance(AES_GCM_NO_PADDING_ALGORITHM)

            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val iv = cipher.iv
            if (iv == null || iv.size != IV_LENGTH_BYTES) {
                // Keystore가 IV를 예상대로 생성하지 않은 경우에 대한 방어 코드
                Log.e("AESEncrypt", "Keystore did not generate a valid IV. IV is null or length is not $IV_LENGTH_BYTES. IV length: ${iv?.size}")
                return null
            }
            val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))

            // IV와 암호문을 결합하는 로직은 동일하게 유지
            val combinedPayload = ByteArray(iv.size + encryptedBytes.size)
            System.arraycopy(iv, 0, combinedPayload, 0, iv.size)
            System.arraycopy(encryptedBytes, 0, combinedPayload, iv.size, encryptedBytes.size)
            Base64.encodeToString(combinedPayload, Base64.NO_WRAP)
        } catch (e: Exception) {
            Log.e("AESEncrypt", "encrypt 함수에서 예외 발생", e)
            null
        }
    }

    fun decrypt(encryptedDataWithIv: String): String? {
        return try {
            val secretKey = getOrCreateSecretKey() // 복호화 시에도 동일한 키를 가져와야 함
            val cipher = Cipher.getInstance(AES_GCM_NO_PADDING_ALGORITHM)

            val decodedData = Base64.decode(encryptedDataWithIv, Base64.NO_WRAP)

            if (decodedData.size < IV_LENGTH_BYTES) {
                // IV 길이보다 짧으면 유효하지 않은 데이터
                return null
            }

            val iv = decodedData.copyOfRange(0, IV_LENGTH_BYTES)
            val encryptedBytes = decodedData.copyOfRange(IV_LENGTH_BYTES, decodedData.size)

            val gcmParameterSpec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)

            val decryptedBytes = cipher.doFinal(encryptedBytes)
            String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}