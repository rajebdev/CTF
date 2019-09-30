import gmpy2
from Crypto.Util.number import *

N1 = 60289643029222274358640373862109572905832367237826183141813583202829591109669612949524019698694013632009103378887037677183876999952454652728808888741248281997406751460346721080506423808100281915419838852944218937480402494149772589869774076589478083083904231331135422435887112387158083457945715084009855703843
N2 = 60289643029222274358640373862109572905832367237826183141813583202829591109669612949524019698694013632009103378887037677183876999952454652728808888741248281997406751460346721080506423808100281915419838852944218937480402494149772589869774076589478083083904231331135422435887112387158083457945715084009855703843

c1 = bytes_to_long("107792f625f117a5bdbb71ed276f0bf3f977401579e37b78c4663ce44e69eea72f4bcdb4eba81e6b1eee3c5ded268e40dc974e352e3d5762a94c3ab83677b40043226e12adcd57cd65e6098b79fe90b40091b353c4e827c29a5862ac311e315040687babdf339d699eebfdc8e7cf688dd4d2564b28402f039b7b768608197405".decode('hex'))
c2 = bytes_to_long("01c2deff9ab4282579bb1e9b2f8d8ce937d13c8f79bc4f3b9e7b2ec332bab4db7a7dd2437fae1c5ef72db26d792576b4872d3086b01386b56f82969399227ec2958400390a91c5e66df5456bbc10cab7316b55d601ea273f60e158636a38a9f50754db6d896000c5d51f614775a1918e2fbc98b9ae4d8c37ffe72a3d393cf192".decode('hex'))

e = 65537

tA = c1 * (N2*N1) * gmpy2.invert(N2*N1, N1)
tB = c2 * (N1*N2) * gmpy2.invert(N1*N2, N2)

c = (tA + tB) % (N1*N2)

m = gmpy2.root(c,e)[0]

print m
