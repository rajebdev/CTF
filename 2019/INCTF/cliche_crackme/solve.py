a2 = list('abcdefghijklmnopqrstuvwxyz')
print(a2)
v6 = len(a2)
v5 = 0
a1 = []
for i in range(0, v6-1):
    for j in range(i+1, v6):
        a1.append(a2[j]+a2[i])

print(a1)
