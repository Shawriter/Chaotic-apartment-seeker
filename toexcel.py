import pandas as pd


class ExcelWriterAndManip:

    def __init__(self, filename, address, minutes, cumulated_minutes):

        self.filename = filename
        self.address = address
        self.minutes = minutes
        self.cumulated_minutes = cumulated_minutes

    def writetoexcel(self, flist):

        try:
            df = pd.DataFrame(list(zip(self.address, self.minutes,
                                       self.cumulated_minutes)),
                              columns=['Address', 'Avg.Minutes',
                                       'Cumulated minutes'])
            df.to_excel(self.filename+'.xlsx',
                        index_label="Nro",
                        merge_cells=False, encoding='utf-8')

        except Exception as e:
            raise e
