flag = open("flag_surabaya.txt", "r").read().split('\n')
import base64
import hashlib
liss = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890'
target = 'b6991c1a4945060a62f727e3086c4f5f608681b1'
##print(flag[1])
for find in flag[:-1]:
    find = find[::-1]
    find = base64.b64decode(find)
    flags = find
##    print(find)
    find = find[::-1]
##    print(find)
    find = find[5:15]
##    print(find)
    if hashlib.sha1(find).hexdigest() == target:
        print(flags)
