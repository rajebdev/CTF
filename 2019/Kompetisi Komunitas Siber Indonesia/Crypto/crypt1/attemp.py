# enc = [
#     229,
#     9223372036854775869,
#     225,
#     9223372036854775870,
#     243,
#     55,
#     165,
#     9223372036854775828,
#     245,
#     9223372036854775869,
#     217,
#     59,
#     239,
#     55,
#     229,
#     56,
#     243,
#     41,
#     87,
#     60,
#     237,
#     58,
#     233,
#     9223372036854775853,
# ][::-1]

enc = [
    256,
    9223372036854775845,
    178,
    47,
    196,
    52,
    232,
    9223372036854775865,
    208,
    53,
    226,
    9223372036854775857,
    252,
    9223372036854775865,
    230,
    9223372036854775870,
    220,
    9223372036854775865,
    240,
    9223372036854775857,
    264,
    9223372036854775865,
    264,
    9223372036854775868,
    170,
    9223372036854775883,
    136,
    9223372036854775830,
    122,
    23,
    168,
    9223372036854775847,
    172,
    9223372036854775843,
][::-1]


def rotl(num, bits=64):
    bit = num & (1 << (bits - 1))
    num <<= 1
    if bit:
        num |= 1
    num &= 2 ** bits - 1
    return num


def rotr(num, bits=64):
    num &= 2 ** bits - 1
    bit = num & 1
    num >>= 1
    if bit:
        num |= 1 << (bits - 1)
    return num


for i in range(128):
    encxor = []
    for j in enc:
        encxor.append(i ^ j)
    # print(encxor)
    flagr = []
    for x, y in enumerate(encxor):
        if x % 2 == 0:
            flagr.append(rotl(y))
        else:
            flagr.append(rotr(y))
    # print(flagr)
    for y in range(128):
        flag = ""
        for z in flagr:
            try:
                flag += chr(z - y)
            except:
                pass
        if "KKSI" in flag:
            print(flag)

