import random
import base64


def shuffle_string(s):
    s = list(s)
    random.shuffle(s)
    return "".join(s)
count = 0
while count <= 10:
    b64  = "QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5ejAxMjM0NTY3ODkrL0FCQ0RFRkdISUpLTE1OT1BRUlNUVVZXWFlaYWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXowMTIzNDU2Nzg5Ky9BQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWmFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6MDEyMzQ1Njc4OSsvQUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5ejAxMjM0NTY3ODkrL0FCQ0RFRkdISUpLTE1OT1BRUlNUVVZXWFlaYWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXowMTIzNDU2Nzg5Ky8="
    b64 += shuffle_string("77ff")
    b64t = "5O3LN8lWNzf3R14sqOx/OmmROvNllGTgUlnfg071wUwQAWGpADh4J02Pdy3BTIlDTFfxio6hsosz7qgFVL1ZCzme5zNmN1TYROnCq8vVqvcNOG7OllwyUmGAgU3owWl0wDfnA04EJUxMdImtdFNvTQTXiykPsqYB7LOD7Brx+t2c5178NOwIR8G+Rzh7q125Ol3qlmlUlvfwU0mHgDNGw0TkAUnZJWv9JFchdQ7zTywFiIGjsL8tsB5v7odXVREM5O3LN8lWNzf3R14sqOx/OmmROvNllGTgUlnfg071wUwQAWGpADh4J02Pdy3BTIlDTFfxio6hsosz7qgFVL1ZCzme5zNmN1TYROnCq8vVqvcNOG7OllwyUmGAgU3owWl0wDfnA04EJUxMdImtdFNvTQTXiykPsqYB7LOD7Brx+ta="
    b64t += shuffle_string("KubS")
    urut = []
    for i in range(len(b64)):
        if (b64t[i]+' = '+b64[i]) not in urut and b64[i] != '=':
            urut.append(b64t[i]+' = '+b64[i])
    print(len(urut))
    ##urut.sort()
    ##for i in urut:
    ##    print(i)
    inp = "sL8tsvNkwRc0JWmQYWGBVHcLqzv5N1lqlL8hiDGByFNkAy7bwywGJG2odQGPTW2Qd0mPAIGbJW2Ebq5v7odXVUmHgDNGw0TkAUnZJWv9JFchdQ7zTywFiIGj5O3LN8lWNzf3R14sqOx/OmmROvNllGTgUlkfYHs13RgQ+e1p+tP4CHajVBP2/o26UvhTyG2riFh2bo6hsosz7qgFVLGfg071wUwQAWGpADh4J02Pdy3BTIlDTFfxi1me5zNmN1TYROnCq8vVqvcNOG7OllwyUmGAYRYo3eO03trn+HEECRXMVoEa/qXK5m4dylxbgI4abyXPsqYB7LOD7BrxgU3owWl0wDfnA04EJUxMdImtdFNvTQTXiync5178NOwIR8G+Rzh7q125Ol3qlmlUlvfwUH8HYt5G3Hdk+RkZCez9CBkS/Lzu/zcJymviyDcSbIvusL8tsB5v7odXVUmHgDNGw0TkAUnZJWv9JFchdQ7zTywFiIGj5O3LN8lWNzf3R14sqOx/OmmROvNllGTgUlkfYHs13RgQ+e1p+tP4CHajVBP2/o26UvhTyG2riFh2brk="
    hasil = ""
    for i in inp:
        get = i
        if i in b64t:
            x = b64t.index(i)
            get = b64[x]
        hasil += get
        
    res = base64.b64decode(hasil)
    print(res)
    count += 1
