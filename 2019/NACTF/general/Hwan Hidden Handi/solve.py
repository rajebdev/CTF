plain = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0,.,/,-,_,=,:"
chiper = "T,v,m,9,M,j,=,S,a,i,w,k,e,C,P,L,X,D,J,c,8,h,f,_,.,t,I,B,q,R,Q,Z,U,n,K,u,l,E,-,7,6,g,N,p,/,s,Y,3,:,4,o,A,x,H,G,1,b,F,W,2,z,r,y,d,O,V,5,0"


plain_list = plain.split(",")
chipper_list = chiper.split(",")

flag = "SccLJ0ddkSGy=PP=kM8JMDmPCcMCcymPedh9_r_GwDtt.::/.1TS_Ba:uU9KNpzir:VcNEVK/PPDXCImKlqK8rqtfOAvisA2MIikfjEq1ReFNC/gi_bf5fbrOSxrODf"


for i in flag:
    print(plain_list[chipper_list.index(i)], end="")

# hilangkan ukuran agar tidak buram
