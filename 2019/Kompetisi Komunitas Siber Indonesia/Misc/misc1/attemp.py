from pwn import *

r = remote("192.168.3.100", 6699)

while True:
    try:
        angka = r.recvuntil("=> ")
        log.info(angka)
        a1 = angka.split()[-4]
        op = angka.split()[-3]
        a2 = angka.split()[-2]
        log.info(a1+op+a2)
        ans = str(eval(a1+op+a2))
        log.info(ans)
        r.sendline(ans)
        log.info(ans)
    except:
        log.info(r.recv())
