'''import linecache
    #filePath = 'out.txt'
filePath = 'd:\\temp\\js-test-2014-11-14.js'
#    start= 874
#    end = 916
for i3 in range(33):
    line = linecache.getline(filePath, i3).rstrip('\n')
    print line'''

file_name = 'out.txt'
def test_seek():
    file_object = open(file_name, 'r')
    file_object.seek(90)
    for line_i in file_object.read().splitlines():
        print line_i
#    file_object.write("dddddddddddddddddddddddddddddddddd".encode('gbk'))
    file_object.close()
#    for line_i in file_object.read().splitlines():
#        print line_i
#    file_object.close()  
    #list_of_text_strings = ['a','b','c']
    #file_object.writelines(list_of_text_data)
def getline(thefilepath, desired_line_number):
    if desired_line_number < 1: return ''
    for current_line_number, line in enumerate(open(thefilepath, 'rU')):
        if current_line_number == desired_line_number - 1:return line
    return ''
def read_file():
    list1 = []
    list2 = []
    file_object = open(file_name, 'r')
    count = 0
    for line_i in file_object.read().splitlines():
        count = count+len(line_i)
        if count<130:list1.append(line_i)
        else: list2.append(line_i)
    str = '--------------'   
    list1.append(str)
    for dict_i in list2:
        list1.append(dict_i)
    return list1

def add_line_no():
    read_lines = open(file_name, 'r').readlines()
    file_object = open(file_name, 'w')
    count_i = 0
    for line in read_lines:
        count_i = count_i + 1
        file_object.write(str(count_i)+","+ line)
    file_object.close()   
    
if __name__ == '__main__':
#    add_line_no()
#    test_seek()
#     for line in read_file():
#         print line
     print getline(file_name, 5) 
     import file_model
     file_model.get_diff();
