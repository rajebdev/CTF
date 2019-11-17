import hashlib

flag = open("find_fix.txt", "r").read().replace('?', '{}').split('\n')

liss = '1234567890abcdef'
target = '4127539692df051c972b16a459ec9591'

for i in liss:
    for j in liss:
        for find in flag:
            payload = find.format(i,j)
            if hashlib.md5(hashlib.md5(payload).hexdigest()).hexdigest() == target:
                print "FLAG : KKSI2019{%s}" % payload
