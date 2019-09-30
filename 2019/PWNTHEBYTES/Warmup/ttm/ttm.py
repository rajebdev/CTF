from Crypto.Cipher import AES
from Crypto.Util import Counter
from os import urandom

with open("secret_key", "rb") as key_file_fd:
    KEY = key_file_fd.read(16)

with open("flag") as flag_fd:
    FLAG = flag_fd.readline().strip()

print("key =", bytes.hex(KEY), "length =", len(KEY))
# key = REDACTED length = 16
nonce = urandom(8)
print("nonce =", bytes.hex(nonce))
# nonce = f0d27667d7000df9
ctr = Counter.new(128, initial_value=int.from_bytes(nonce, "big"))
aes_cipher = AES.new(KEY, AES.MODE_CTR, counter=ctr)
flag = FLAG.encode("utf-8")
enc_flag = aes_cipher.encrypt(flag)
print("encrypted flag =", bytes.hex(enc_flag))
# encrypted flag = 63242f9d6c4c748231602c990c0b6a51dbc0ca0ea0f26a780fe613
