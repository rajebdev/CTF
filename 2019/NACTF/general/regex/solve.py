file = open("flag.txt", "r")
data = file.readlines()


def cekVoc(text):
    text = list(text)
    valid = True
    i = 0
    while valid and i < 7:
        if text[i] not in ['a','i','u','e','o']:
            valid = False
        i+=1
    return valid

for x in data:
    if cekVoc(x[-9:-2]):
        print(x)


