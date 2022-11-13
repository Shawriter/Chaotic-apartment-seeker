import toexcel
# Run in the terminal or Windows cmd if Python is in the environment path
# Add bin line if you run in the Linux environment
''' This script's purpose is to parse and analyze the data from the chaotic
apartment seeker. Will also use this to manipulate other data files. When the
object structures are more anonymous/abstract. For now its specific for this
one project..'''
# Development notes---->
# Assign structures and gridnode layout make a module
# Ripplemodule ripple route google maps street sprite sheets
# Shortest path and the area between addresses


def main():

    try:
        minutelimit = input("Write a number between 0-300(minutes): ")
        readfile(minutelimit)

    except Exception as e:
        raise e


def addtofinal_list(addrlist_1, avglist_2, cumulatedlist_3):

    final_list = []
    final_list_indexcalc = 0
    # Unpacking dictionary and adding to the final_list
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

    # print(final_list)
    write_to_file(final_list, addrlist_1, avglist_2, cumulatedlist_3)


def readfile(minutesarvo):

    osoitedict = {}
    osoite_list_1 = []
    avgmin_list_2 = []
    cumulatedmin_list_3 = []

    bool = 0
    minbool = 0.0
    arvocalc = 0
    m = float(minutesarvo)
    n = 1
    n2 = 1
    n3 = 1

    # Adding everything to a dictionary, this structure will be used later

    with open('Addresses_and_averages.txt', 'r') as f:

        for line in f:

            if "Destination" in line:
                osoite = line.split(":")
                osoitedict["Destination" + str(n)] = osoite[1]
                n += 1
            if "Avg.time" in line:
                aika = line.split(":")
                osoitedict["Avg.time" + str(n2)] = aika[1]
                n2 += 1
            if "Cumulated" in line:
                cumulated_mins = line.split(":")
                osoitedict["Cumulatedminutes" + str(n3)] = cumulated_mins[1]
                n3 += 1

    for arvo in osoitedict.values():

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
    print(cumulatedmin_list_3)
    addtofinal_list(osoite_list_1, avgmin_list_2, cumulatedmin_list_3)


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


if __name__ == "__main__":
    main()
