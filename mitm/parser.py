def parse(data, port, origin):
    if port==25565:
        return
    if origin == 'server':
        return
    print("[{}({})] {}".format(origin, port, data.encode('hex')))
