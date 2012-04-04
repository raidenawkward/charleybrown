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

	img_name=${image%.*}
	img_num=${img_name##*img}
	dir="$OUTPUT_DIR/dish$img_num"
	mkdir -p $dir

	declare -i img_num
	let mod=img_num%2
	if [ $mod -eq 0 ]
	then
		tags="even test"
	else
		tags="odd test"
	fi

	gen_xml "$dir/$xml" "$img_name" $img_num "0.0" "$tags" "0.0" "summary of $image" "this is the detail of $image" "$image" "$image"

	$CP $IMAGE_DIR/$image $dir
	echo dish $dir/$xml generated
done

sync
echo all $i done
