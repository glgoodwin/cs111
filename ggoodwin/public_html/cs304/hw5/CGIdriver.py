#!/usr/local/bin/python2.7
# PARSES THE FORM and invokes the other python file
import cgi
import cgitb; cgitb.enable()
import os

import hw5py2
from cgi_utils_sda import file_contents,print_headers

## month_names = ['Choose Month',
##                'January', 'February', 'March', 'April', 'May', 'June',
##                'July', 'August', 'September', 'November', 'December']

## def generate_month_menu(selected_index):
##     lines = []
##     for i in range(len(month_names)):
##         sel = 'selected' if selected_index == i else ''
##         lines.append(
##             "<option value='{0} {1}'>{1}</option>"
##             .format(i,sel,month_names[i]))
##     return "\n".join(lines)

def main():
    form_data = {'month': ''}
    fillers = {}
    fillers['scriptname'] = os.environ['SCRIPT_NAME'] if 'SCRIPT_NAME' in os.environ else ''

    fs = cgi.FieldStorage()
    if 'month' in fs:
        month = int(fs.getFirst('month'))
        fillers['monthname'] = month_names[month]
        fillers['actorlist'] = actorNamesByMonth.main(month)
        fillers['monthmenu'] = generate_month_menu(month)
    else:
        fillers['monthname'] = 'None'
        fillers['actorlist'] = ''
        fillers['monthmenu'] = generate_month_menu(0)

    tmpl = file_contents('actorNamesByMonth.tmpl')
    fillers['footer'] = file_contents('footer.part')
    return tmpl.format(**fillers)

if __name__ == '__main__':
    print_headers(None)
    print main()
