from PIL import Image
import sys



imagepath = "render.ppm"
arguments = sys.argv[1:]

try:
    with open(imagepath, 'r') as file:
        lines = file.readlines()
        dimensions = lines[1].split()
        width = dimensions[0]
        height = dimensions[1]
        color = lines[2].strip()

        image = Image.new("RGB", (int(width), int(height)))

        pixelArray = []
        pixelRow = []

        pixelIndex = 0
        y = 0
        x = 0
        while y < int(height):
            while x < int(width):
                #print(f"{x},{y}")
                rgbList = lines[pixelIndex+3].split()
                pixel = (int(rgbList[0]),int(rgbList[1]),int(rgbList[2]))
                #pixelRow.append(pixel)
                image.putpixel((x,y), pixel)

                x+=1
                pixelIndex += 1 
            #pixelArray.append(list(pixelRow))
            #pixelRow.clear()

            x = 0
            y +=1
        file.close()
 


except Exception as e:
    print("Error: ")
    print(e)

#print(dimensions)
#print(width)
#print(height)
#print(color)

#print(pixelArray[100])
rendername = "render.png"
if len(arguments) > 0:
    if arguments[0]=="jpg":
        rendername = f"render.{arguments[0]}"
image.save(rendername)
