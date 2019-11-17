flag = "Nikko Enggaliano Ganteng"
bf   = []
sr   = []

def rr(string, key):
	ret = ""
	for x in string:
		ret += chr( (ord(x) + key) )
	return ret



def rotl(num, bits=64):
    bit = num & (1 << (bits - 1))
    num <<= 1
    if (bit):
        num |= 1
    num &= (2 ** bits - 1)
    return num


def rotr(num, bits=64):
    num &= (2 ** bits - 1)
    bit = num & 1
    num >>= 1
    if (bit):
        num |= (1 << (bits - 1))
    return num

flag = rr(flag, 11)

for x,j in enumerate(flag):
	cv = ord(j)
	if x % 2 == 0:
		print("Genap", cv ,rotr(cv), x)
		bf.append(rotr(cv))
	else:
		print("Ganjil", cv ,rotl(cv))
		bf.append(rotl(cv))

print(bf)

for i in bf:
	final = i ^ 1
	sr.append(final)



print(sr[::-1])
