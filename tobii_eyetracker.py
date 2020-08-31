import tobii_research as tr
import time
import socket
import sys
import datetime


from signal import signal, SIGINT

def gaze_data_callback(gaze_data):
    #gaze_data["system_time_stamp"]
    string = f'{int(datetime.datetime.timestamp(datetime.datetime.now()) * 1000000)};{gaze_data["left_pupil_diameter"]};{gaze_data["right_pupil_diameter"]};{gaze_data["left_gaze_point_on_display_area"][0]};{gaze_data["left_gaze_point_on_display_area"][1]};{gaze_data["right_gaze_origin_in_user_coordinate_system"][2]}\n'
    s.send(str.encode(string))


def exit_handler(signal_received, frame):
    print('Exiting...')
    my_eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)
    exit(0)

if __name__ == '__main__':
    signal(SIGINT, exit_handler)
    s = socket.socket()
    host_ip = socket.gethostbyname('vm-weber03.ics.unisg.ch')
    s.connect((host_ip, 8091))

    found_eyetrackers = tr.find_all_eyetrackers()
    my_eyetracker = found_eyetrackers[0]
    print("Address: " + my_eyetracker.address)
    print("Model: " + my_eyetracker.model)
    print("Name (It's OK if this is empty): " + my_eyetracker.device_name)
    print("Serial number: " + my_eyetracker.serial_number)


    my_eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)

    while True:
        """gaze_data_callback({
            'left_pupil_diameter': 3.8292083740234375,
            'right_pupil_diameter': 3.8292083740234375,
            'left_gaze_point_on_display_area': (0.5197135806083679, 0.7318005561828613, 0.640562117099762),
            'right_gaze_origin_in_user_coordinate_system' : (-9.551579475402832, -112.31156158447266, 692.1686401367188)
        })"""
        time.sleep(0.00001)