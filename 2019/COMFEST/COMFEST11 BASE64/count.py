urut = []
while True:
    inp = input("Hitung = ")
    for i in inp:
        if i not in urut:
            urut.append(i)

    urut.sort()
    print(len(urut), urut)
    urut = []
