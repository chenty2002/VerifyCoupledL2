with open('VerifyTop.sv') as fin:
    with open('VerifyTop_jg.sv', 'w') as fout:
        linnnes = fin.readlines()
        lines = []
        for line in linnnes:
            if '.site' in line:
                indx = line.index('.site')
                flag = False
                i = indx
                while line[i] != ')':
                    i += 1
                lines.append(line[:indx] + line[i+1:])
            else:
                lines.append(line)

        lout = []
        i = 0
        while i < len(lines):
            # print(i)
            if 'assert' in lines[i] or '$fwrite' in lines[i]:
                j = i-1
                flag = False
                while j >= 0:
                    # print('j: '+str(j))
                    if '$fwrite' not in lines[i] and ('match_tag' in lines[j] or 'resetCounter_notChaos' in lines[j]):
                        flag = True
                        break
                    if 'if' in lines[j]:
                        break
                    j -= 1
                k = i+1
                while k < len(lines):
                    # print('k: '+str(k))
                    if 'end' in lines[k].split():
                        break
                    k += 1
                i = k
                lout = lout[:j]
                for l in range(j, k+1):
                    if flag:
                        lout.append(lines[l])
                    else:
                        lout.append('// ' + lines[l])
            else:
                lout.append(lines[i])
            i += 1
            # if i == 2111:
            #     break
        for line in lout:
            if not line.startswith('//'):
                fout.write(line)