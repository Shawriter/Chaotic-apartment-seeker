import toexcel
# Run in the terminal or Windows cmd if Python is in the environment path
# Add bin line if you run in the Linux environment
''' This script's purpose is to parse and analyze the data from the chaotic
apartment seeker. Will also use this to manipulate other data files. When the
object structures are more anonymous/abstract. For now its specific for this
one project..'''
# Development notes---->
'''DONE Ripplemodule ripple route google maps street sprite sheets DONE with C and opengl'''
# Shortest path and the area between addresses transposing between planes

def main():

    try:
        minutelimit = input("Write a number between 0-300(minutes): ")
        readfile(minutelimit)

    except Exception as e:
        raise e

def readfile(minutesarvo):

    osoitedict = {}
   
    """bool = 0
    minbool = 0.0
    arvocalc = 0"""
    m = float(minutesarvo)
    n = 1
    n2 = 1
    n3 = 1
    
    # Adding everything to a dictionary, this structure will be used later
    # Assigning a flag to represent the value in which category the address and time belongs to greater or less than
    with open('Addresses_and_averages.txt', 'r') as f:

        for line in f:

            if "Startpoint" in line:
                osoite = line.split(":")
                osoitedict["Startpoint" + str(n)] = osoite[1]
                n += 1
            if "Avg.time duration" in line:
                aika = line.split(":")

                if float(aika[1]) <= m:

                    osoitedict["Avg.time" + str(n2)] = str(aika[1]) + "lessthan_flag" + str(n2) + "avg"
                    osoitedict["Startpoint" + str(n-1)] = osoite[1] + "lessthan_flag" + str(n2) + "str"

                if float(aika[1]) >= m:

                    osoitedict["Avg.time" + str(n2)] = str(aika[1]) + "greaterthan_flag" + str(n2) + "avg"
                    osoitedict["Startpoint" + str(n-1)] = osoite[1] + "greaterthan_flag" + str(n2) + "str"
                n2 += 1
            if "Cumulated minutes" in line:
                cumulated_mins = line.split(":")
                # osoitedict["Cumulatedminutes" + str(n3)] = cumulated_mins[1]
                if float(aika[1]) <= m:

                    osoitedict["Cumulatedminutes" + str(n3)] = str(cumulated_mins[1]) + "lessthan_flag" + str(n3) + "cumulated"
                    
                if float(aika[1]) >= m:

                    osoitedict["Cumulatedminutes" + str(n3)] = str(cumulated_mins[1]) + "greaterthan_flag" + str(n3) + "cumulated"

                n3 += 1

    #print(osoitedict)
    split_dict(osoitedict, m)
    
def split_dict(osoitedict, m):
    
    start_p_iter_less = 1
    avg_iter_less = 1
    cumulated_iter_less = 1
    
    osoite_list_lessthan = []
    avgmin_list_lessthan = []
    cumulatedmin_list_lessthan = []
   
    """osoite_list_greaterthan = []
    avgmin_list_greaterthan = []
    cumulatedmin_list_greaterthan = []"""
    
    # Append the values from the dictionary into their own lists
    for arvo in osoitedict.values():
          
        
        if "lessthan_flag" in arvo:

          arvo_2 = arvo.split("\nl")
         
          if "str" in arvo:

            osoite_list_lessthan.append(arvo_2[0])
            start_p_iter_less += 1

          if "avg" in arvo:

            avgmin_list_lessthan.append(arvo_2[0])
            avg_iter_less += 1

          if "cumulated" in arvo:

            cumulatedmin_list_lessthan.append(arvo_2[0])
            cumulated_iter_less += 1
    
    FinalList.to_final(osoite_list_lessthan, avgmin_list_lessthan, cumulatedmin_list_lessthan)

    """print(osoite_list_lessthan)
    print(avgmin_list_lessthan)
    print(cumulatedmin_list_lessthan)
    if "greaterthan_flag" in arvo:
          
          arvo_3 = arvo.split("greaterthan_flag" + str(n))
          #print(arvo_3[0])
          osoite_list_greaterthan.append(arvo_3[0])
          avgmin_list_greaterthan.append(arvo_3[0])
          cumulatedmin_list_greaterthan.append(arvo_3[0])"""

    """for arvo in osoitedict.values():

        try:
            arvotofloat = float(arvo)
            if arvotofloat:
                if (arvocalc == 1):
                    arvocalc += 1
                    print(arvocalc)
                    avgmin_list_2.append(osoitedict.get("Avg.time1"))
                if (arvotofloat <= m):
                    avgmin_list_2.append(arvo)
                    minbool == arvo

                if (arvotofloat > 100):
                    minbool == arvo
                    cumulatedmin_list_3.append(arvo)

        except Exception:
            if (minbool < 1) and (bool != 1):
                bool = 1
            if isinstance(minbool, float):
                arvocalc += 1
                osoite_list_1.append(arvo)

            continue
    print(cumulatedmin_list_3)"""
    
    
    '''if m <= float(avgmin_list_greaterthan[0]):
      addtofinal_list(osoite_list_lessthan, avgmin_list_lessthan, cumulatedmin_list_lessthan)

    if m >= float(avgmin_list_lessthan[0]):
      addtofinal_list(osoite_list_greaterthan, avgmin_list_greaterthan, cumulatedmin_list_greaterthan)'''


def write_to_file(flist, addrs, avgmins, cumulated):

    try:
        answer = input("Save the final file as and .xls or .txt file? 'x' for xls 't' \
for txt:")

        if answer == "x":
            answer_2 = input("Name the file:")
            xlsfile = toexcel.ExcelWriterAndManip(answer_2, addrs, avgmins,
                                                  cumulated)
            xlsfile.writetoexcel()
            print("File " + answer_2 + ".xls saved")
        elif answer == "t":
            answer_2 = input("Name the file:")
            try:
                with open(answer_2 + ".txt", 'w+') as txtfile:
                    for txtline in flist:
                        txtfile.write(txtline)
                print("File " + answer_2 + ".txt saved")
            except Exception as e:
                print(e)
                return 0
        elif answer != ("x" or "t"):
            print("Choose x or t!")
        else:
            return 0
    except Exception as e:
        raise e

class FinalList:

            def to_final(addrlist_1, avglist_2, cumulatedlist_3):

                final_list = []
                final_list_indexcalc = 0
                
                for addr in addrlist_1:
                    final_list.append(addr)
                    final_list_indexcalc += 1
                    if final_list_indexcalc <= 1:
                        final_list.append(avglist_2[0])
                        final_list.append(cumulatedlist_3[0])
                    else:
                        try:
                            final_list.append(avglist_2[0 + final_list_indexcalc])
                            final_list.append(cumulatedlist_3[0 + final_list_indexcalc])
                        except Exception:
                            break

                
                write_to_file(final_list, addrlist_1, avglist_2, cumulatedlist_3)

if __name__ == "__main__":
    main()
