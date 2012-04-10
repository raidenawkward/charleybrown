#!/bin/bash
#
# TODO: script for CB testing
# generate dish dir struct with image and xml files from source image dir
# to target dir
#
# AUTHOR: Raiden Awkward <raiden.ht@gmail.com>
# DATE: 2012-03-29
#

IMAGE_DIR=""
OUTPUT_DIR=""

CP="cp -af"

if [ ! -z $1 ] && [ ! -z $2 ]
then
	IMAGE_DIR="$1"
	OUTPUT_DIR="$2"
	echo will generate dishes in $OUTPUT_DIR from source dir $IMAGE_DIR
	sleep 3
else
	echo usage $0 [image source dir] [target dir]
	exit 0
fi

if [ ! -d $IMAGE_DIR ]
then
	echo source image dir \"$IMAGE_DIR\" does not exist
	exit 0
fi

IMAGE_LIST=`ls $IMAGE_DIR`

function gen_xml {
	xml="$1"
	file_name="$2"
	id="$3"
	price="$4"
	tags="$5"
	score="$6"
	summary="$7"
	detail="$8"
	thumb="$9"
	picture="${10}"

	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>" > $xml
	echo "<dish>" >> $xml
	echo "	<id>$id</id>" >> $xml
	echo "	<name>$file_name</name>" >> $xml
	echo "	<price>$price</price>" >> $xml

	echo "	<tags>" >> $xml
	for tag in $tags
	do
		echo "		<tag>$tag</tag>" >> $xml
	done
	echo "	</tags>" >> $xml

	echo "	<score>$score</score>" >> $xml
	echo "	<summary>$summary</summary>" >> $xml
	echo "	<detail>$detail</detail>" >> $xml
	echo "	<thumb>$thumb</thumb>" >> $xml
	echo "	<picture>$picture</picture>" >> $xml

	echo "</dish>" >> $xml
}

declare -i i
i=0
for image in $IMAGE_LIST
do
	i=i+1
	xml="${image%.*}.xml"

	img_name=测试图片${image%.*}
	img_num=${img_name##*img}
	dir="$OUTPUT_DIR/dish$img_num"
	mkdir -p $dir

	declare -i img_num
	let mod2=img_num%2
	if [ $mod2 -eq 0 ]
	then
		price=12.0
		tags="偶数的标签"
	else
		price=33.5
		tags="奇数的标签"
	fi

	let mod10=img_num%10
	if [ $mod10 -eq 0 ]
	then
		price=125.0
		tags=$tags" 整十的标签"
	fi

	tags=$tags" 全部标签"
	gen_xml "$dir/$xml" "$img_name" $img_num "$price" "$tags" "0.0" "对于$image的简短介绍" "这里是对于$image的详细介绍,在这里会出现大量的文字,但是在android系统中,对中文的显示会存在编码问题,目前采用手动的方式对xml配置文件的内容进行设定,以避免在程序中出现乱码显示的问题,理想的方法是找到一种方法能够动态地对文件的编码方式进行识别" "$image" "$image"

	$CP $IMAGE_DIR/$image $dir
	echo dish $dir/$xml generated
done

sync
echo all $i done
