def get_diff():
    test1filehandle = open("test1.txt", "r") #creating a file handle
    test2filehandle=open("test2.txt","r") #creating a file handle to read
    test3filehandle=open("test3.txt","w") #creating a file handle to write
    test1list= test1filehandle.readlines() #read the lines and store in the list
    test2list=test2filehandle.readlines()
    k=1
    for i,j in zip(test1list,test2list): #zip is used to iterate the variablea in 2 lists simultaneoously   
        if i !=j:
            test3filehandle.write("Line Number:" +str(k)+' ')
            test3filehandle.write(i.rstrip("\n") + ' '+ j)
        k=int(k)
        k=k+1;
'''test'''        
dict1={1:[1,11,111],2:[2,22,222]}
dict2={3:[3,33,333],2:[2,44,444]}

dictMerged1=dict(dict1.items()+dict2.items())
print dictMerged1
print dict(dict1, **dict2)

dic1 = [2,22,222]
dic2 = [2,21,222]
print dict(dic1.items()+dic2.items())





