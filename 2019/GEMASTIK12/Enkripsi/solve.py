from hashlib import md5
import string
from itertools import product, combinations, permutations


onecapital = string.ascii_uppercase
lowercase = string.ascii_lowercase
onenumber = string.digits
underscore = '_'

for s in product(onecapital, lowercase, lowercase, onenumber, underscore):
  for combo in permutations(s):
    word = ''.join(combo)
    flag = word
    md5hash = md5(flag.encode()).hexdigest()
    if md5hash == 'daf42e9a7b8574d646de022211c89e357ff629d1c613f4e3':
      print(flag)
print('Done')
