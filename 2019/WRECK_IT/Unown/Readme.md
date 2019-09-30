```
disediakan file bin
setelah dibuka didapatkan kumpulan biner berikut

01 01 01 01 01 00 01 01 00 01 00 00 01 01 01 00 00 00 01 00 01 01 00 01 01 01 01 01 01 01 00 01 01 00 01 01 01 01 00 01 00 01 01 01 00 01 01 01 00 01 01 00 01 00 00 01 01 01 00 01 01 00 01 01 00 01 01 01 01 01 01 00 01 00 00 01 01 01 00 00 00 00 00 01 01 00 00 01 01 01 00 01 00 00 00 00 00 01 01 00 01 00 00 00 00 01 00 01 01 01 01 01 01 01 00 01 00 00 00 01 01 00 00 00 00 01 01 01 00 00 01 00 00 01 01 01 00 01 00 01 01 01 01 00 00 01 01 01 00 01 01 01 01 01 01 01 01 00 01 00 01 01 01 00 00 00 00 01 01 01 00 01 01 00 00 01 01 00 00 00 00 01 01 00 00 01 00 01 01 01 01 01 01 00 01 01 01 01 00 01 00 01 01 01 01 00 00 00 01 01 01 01 00 00 01 00 01 01 01 01 00 00 01 00 01 01 01 00 01 01 01 01 00 00 00 00 00 01 00 01 00 00 01 01 01 01 00 01 00 01 00 00 00 00 00 01 00 01 01 01 00 01 00 01 01 01 00 01 01 00 00 00 00 01 01 01 01 01 00 00 01 01 00 00 00 00 00 01 01 00 01 00 00 00 01 01 00 00 00 00 01 01 01 00 01 01 00 00 01 00 00 00 00 00 01 01 00 01 01 01 00 01 01 00 00 00 00 01 01 01 01 00 00 01 01 01 01 00 00 00 00 01 01 01 00 01 01 01 00 01 01 00 00 00 00 01 01 01 00 01 01 00 01 01 01 00 00 01 00 01 01 01 01 00 00 00 00

kemudian dilakukan pengubahan pada :
01 menjadi 1
00 menjadi 0

kemudian dihilangkan spasinya maka didapatkan kumpulan biner berikut pada variabel biner
```

```python
biner = "11111011010011100010110111111101101111010111011101101001110110110111111010011100000110011101000001101000010111111101000110000111001001110101111001110111111110101110000111011001100001100101111110111101011110001111001011110010111011110000010100111101010000010111010111011000011111001100000110100011000011101100100000110111011000011110011110000111011101100001110110111001011110000"
```

```
setelah melihat ascii tabel, diketahui biner yang readable hanya sampai digit biner ke 7 sehingga dilakukan pemecahan dari variabel biner menjadi 7 digit kemudian ditambahkan 0 di depan masing masing 7 digitnya
```

```python
start = 0
binsolve = ''

for i in range(1, len(biner)//7 + 1):
    binsolve += '0'+biner[start:7*i]
    # print('0'+biner[start:7*i])
    start = 7*i

print(binsolve)
```
```
kemudian didecode hasil dari binsolved

}SE_munim_SANAP_hadus_ualaK{kcerw A'TW0|`halA]CgC]C[K

kemudian dilakukan putar balik dengan reverse
```

```python
bindecode = ```}SE_munim_SANAP_hadus_ualaK{kcerw A'TW0|`halA]CgC]C[K```

bindecode = list(bindecode)
bindecode.reverse()

flag = "".join(bindecode)

print("flag is = ",flag)
```
### flag is =  K[C]CgC]Alah`|0WT'A wreck{Kalau_sudah_PANAS_minum_ES}
