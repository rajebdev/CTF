```bash
[+] Opening connection to 192.168.3.100 on port 6699: Done
[*] Selamat Datang di KKSI 2019 Regional Surabaya
     Untuk 1 Soal memiliki 1 Poin.
    Dapatkan 10 poin untuk membuka flag. Waktu 5 detik.

    No: (1) 7877 * 9953 =>
[*] 7877*9953
[*] 78399781
[*] 78399781
[*]  ~~> 78399781.0 (correct)

    No: (2) 7866 - 9589 =>
[*] 7866-9589
[*] -1723
[*] -1723
[*]  ~~> -1723.0 (correct)

    No: (3) 9290 * 7869 =>
[*] 9290*7869
[*] 73103010
[*] 73103010
[*]  ~~> 73103010.0 (correct)

    No: (4) 3293 + 4715 =>
[*] 3293+4715
[*] 8008
[*] 8008
[*]  ~~> 8008.0 (correct)

    No: (5) 8376 + 1625 =>
[*] 8376+1625
[*] 10001
[*] 10001
[*]  ~~> 10001.0 (correct)

    No: (6) 9621 + 3134 =>
[*] 9621+3134
[*] 12755
[*] 12755
[*]  ~~> 12755.0 (correct)

    No: (7) 6054 + 5520 =>
[*] 6054+5520
[*] 11574
[*] 11574
[*]  ~~> 11574.0 (correct)

    No: (8) 6324 * 5628 =>
[*] 6324*5628
[*] 35591472
[*] 35591472
[*]  ~~> 35591472.0 (correct)

    No: (9) 6272 + 8433 =>
[*] 6272+8433
[*] 14705
[*] 14705
[*]  ~~> 14705.0 (correct)

    No: (10) 4439 - 1450 =>
[*] 4439-1450
[*] 2989
[*] 2989
[*]  ~~> 2989.0 (correct)

    Score: 10


    flag: KKSI2019{Soal_Matematika_EZ_Sekali}

Traceback (most recent call last):
  File "attemp.py", line 18, in <module>
    log.info(r.recv())
  File "/usr/local/lib/python2.7/dist-packages/pwnlib/tubes/tube.py", line 78, in recv
    return self._recv(numb, timeout) or ''
  File "/usr/local/lib/python2.7/dist-packages/pwnlib/tubes/tube.py", line 156, in _recv
    if not self.buffer and not self._fillbuffer(timeout):
  File "/usr/local/lib/python2.7/dist-packages/pwnlib/tubes/tube.py", line 126, in _fillbuffer
    data = self.recv_raw(self.buffer.get_fill_size())
  File "/usr/local/lib/python2.7/dist-packages/pwnlib/tubes/sock.py", line 33, in recv_raw
    raise EOFError
EOFError
[*] Closed connection to 192.168.3.100 port 6699
┌─[✗]─[rajebdev@RAJEB]─[/mnt/d/CTF/2019/Kompetisi Komunitas Siber Indonesia/Misc/misc1]
└──╼ $   
```