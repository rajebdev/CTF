import base64
cek = "012345689ABCDEFGHIJKLMNOPQRSTUVWXYZabcdeghijklmnopqrstuvwxyz"
while True:
    text = input("TEXT = ")
    b64 = base64.b64encode(text.encode()).decode()
    urut = []
    for i in b64:
        if i not in urut and i != "=" and i not in cek:
            urut.append(i)

    urut.sort()
    print(b64)
    print(len(urut),''.join(urut))
##    if len(urut) == 62:
##        print("ENCODE : ")
##        print(text)
##        print("to")
##        print(b64)
##        break
