# This Python file uses the following encoding: utf-8
#经检验发现，只要#encoding: utf-8就行，其他的文字多余的词可以随意更改，或者不要。
__doc__ = """
get the VO comments by parsing VO file on the disk.
note: 
1. only analysis the  'private' and contains charactor '//'s line 
2. only recursive two level. to put it simple, show VO, subVO, not dive further
3. not support dive-in the Map sub-attribute comments.
"""
# import sys
# reload( sys )
# sys.setdefaultencoding('utf-8')
import os
import fnmatch
import types
import sys

'''读文件起始行'''
start_line = 1120
end_line = 1135
#'''接口返回VO对象如: 'FesMaterialRequestVO''''
test_vo = 'MktShopSurveyVO'


basedir = "D:\Downloads\JBPM\jbpm-5.4.0.Final-installer-full\jbpm-installer\workspace\yazuo_erp_branch\src\main\java\com\yazuo\erp"  
subClassDict = {}  # get the sub class attribute's comments
assert type(subClassDict) is types.DictType

def iterfindfiles(path, fnexp):
    for root, dirs, files in os.walk(path):
        for filename in fnmatch.filter(files, fnexp):
            yield os.path.join(root, filename)
  
def printJSON(list):
    space = "    "
    print space+"{"
    for obj in list:
        assert type(obj) is types.DictType 
        for j in obj:
            v = obj[j]
            if type(v) is types.ListType:   #处理 多对对 映射
                print space, j, ":[", subClassDict.get(j), "\n",
                print space+"      {"
                for l in v: 
                    assert type(l) is types.DictType
                    for inner_i in l:
                        print space+"       ", inner_i, ':"",', l[inner_i] 
                print space+"      }" 
                print space+"     ]"
            elif type(v) is types.DictType: #处理 一对一 映射
                if subClassDict.get(j):
                    print space, j, ":{", subClassDict.get(j), "\n",
                    for i1 in v:
#                         print type(i1), i1
                        print space+"       ", i1, ':"",', v[i1] 
                        pass
                    print space+"     }" 
            else:
                print space, j, ':"",', v
                pass
    print space+"}"   
           
def parse(path, stop):
    list = []
    with open(path) as file:
        while 1:
            lines = file.readlines(1000)
            if not lines:
                break
            for line in lines:
                if line.find('private') > 0 and line.find('//') > 0:
        #             print line
    #                 print '############'
                    space = line.find(" ")  
                    a = line.find(";")  
                    comments = line[a + 1:]  
        #             print comments
                    words = str(line).split(" ")
                    attrWithSeim = words[2:3][0] #带分号的属性名称,如:userName;
        #             print attrWithSeim
                    semiIndex = attrWithSeim.find(";");
                    attr = str(attrWithSeim[:semiIndex]).strip() #属性名称
                    attr = '"'+attr+'"'
        #             print attr
                    result = {}
                    result[attr] = str(comments).strip() 
                    
                    # check if contains vo , private List<HouseVO> houses;
                    typeVO = words[1:2][0]
                    if typeVO.find("VO") > 0 and stop != 1:  # do not call $VO recursion
                        subClassDict[attr] = str(comments).strip() 
                        if typeVO.find("VO>") > 0:  # deal with list collection , one-to-many.
                            voFileName = typeVO[typeVO.find("<") + 1: typeVO.find(">")]
                            tempSubPrint = '------not found sub-VO------', voFileName
                            for filename in iterfindfiles(basedir, voFileName + ".java"):
#                                 print filename
                                tempSubPrint = parse(filename, 1)
                            result[attr] = tempSubPrint 
                        elif typeVO.find("VO") > 0:  # deal with one-to-one.
                            voFileName = typeVO
    #                         print len(voFileName),  len(str(voFileName).strip()), voFileName
                            tempSubPrint = '------not found sub-VO------', voFileName
                            for filename in iterfindfiles(basedir, voFileName + ".java"):
#                                 print filename
                                tempSubPrint = parse(filename, 1)  #递归调用
                                # change the list $tempSubPrint to dict
                                userDic = {}
                                for element in tempSubPrint:
                                    for key in element:
                                        userDic[key] = element.get(key)
    #                             print userDic
                            result[attr] = userDic
                    list.append(result)
    #         print list
    #         json_output(list)
    #         printJSON(list)
    return list
#         
    # all_symptom = [u'\u773c', u'\u8179\u90e8', u'\u4e94\u5b98', u'\u53e3\u8154', u'\u8179\u90e8',  
    # u'\u53e3\u8154'] 
    # str_symptom = str(all_symptom).replace('u\'','\'')  
    # print str_symptom.decode("unicode-escape")  
    


def copyToClip(outFile):
    '''copy to Clipboard'''
    import pyperclip
#    pyperclip.copy('The text to be copied to the clipboard.')
#    spam = pyperclip.paste()
    file_object = open(outFile)
    try:
         all_the_text = file_object.read()
         pyperclip.copy(str(all_the_text).encode('gbk'))
    finally:
        file_object.close()
        
import linecache
'''standardlize wiki format and generate code'''
def print_wiki_frame(start, end_line, source_file ):
    start = start+1 #过滤掉第一行描述
    desc_line = linecache.getline(source_file,start)
#    count_line = len(open(source_file, 'rU').readlines())
    for i3 in range(end_line-start):
        print (linecache.getline(source_file,i3+start)).rstrip('\n')
        

wiki_desc1 = '''[{ALLOW view All}]
[{ALLOW edit erp_weixin}]
[{ReferringPagesPlugin}]
[{TableOfContents}]
!!!功能描述
'''
wiki_desc2='''
!!!请求示例
%%prettify 
{{{'''
wiki_desc3= '''
}}}
/%
!!!返回数据
%%prettify 
{{{'''
wiki_desc4='''
}}}
/%
[{ReferringUndefinedPagesPlugin}]'''

wiki_gen1='''{
 "flag": 1,//1: '成功', 2:'失败' 
 "message": "",
 "data": '''
wiki_gen2='''  
}'''
if __name__ == '__main__':
    
    source_file = 'd:\\temp\\js-test-2014-11-14.js'
    outFile = 'out.txt'
    # handle the interface responses
    for filename in iterfindfiles(basedir, test_vo+".java"):
        list = parse(filename, 0)     
        printJSON(list)
        #print to console
        try:
            file_object = open(outFile, 'w') 
            sys.stdout = file_object# print out to file
            print wiki_desc1
            print linecache.getline(source_file,start_line).replace('//', '') #功能描述
            print wiki_desc2
            print_wiki_frame(start_line, end_line, source_file)#请求示例
            print wiki_desc3
            print wiki_gen1
            printJSON(list)
            print wiki_gen2
            print wiki_desc4
        finally:
            file_object.close()
    '''copy to Clipboard'''
    copyToClip(outFile)
