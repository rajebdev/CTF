file = open("flag num bagi 8.txt", "r")
rd = file.readlines()
numberList = rd[0].split(", ")
for i in numberList:
    print(chr(int(i)//8), end="")

