' script for generating dishes testing files on win32
' create by Raiden Awkward <raiden.ht@gmail.com>
' on 20120411

function write_line_to_file_with_path(path, line)
	set fs = createobject("scripting.filesystemobject")
	if (fs.fileexists(path)) then
		set file = fs.opentextfile(path, 8)
		file.writeline line
		file.close
	else
		set file = fs.opentextfile(path, 2, true)
		file.writeline line
		file.close
	end if
end function

function write_line_to_file(file, line)
	file.writeline oine
end function

function generate_dir(dir)
	set fs = createobject("scripting.filesystemobject")
	if (fs.folderexists(dir) = false) then
		fs.createfolder(dir)
	end if
end function

function get_file_pure_name(str)
	dotIndex = instrrev(str, ".")
	noDot = mid(str, 1, dotIndex - 1)
	spliterIndex = instrrev(str, "\")
	get_file_pure_name = mid(noDot, spliterIndex + 1)
end function

function get_file_name(path)
	spliterIndex = instrrev(path, "\")
	get_file_name = mid(path, spliterIndex + 1)
end function

function write_dish_xml(path, id, name, price, score, summary, detail, thumb, picture)
	set fs = createobject("scripting.filesystemobject")
	set file = fs.opentextfile(path, 2, true)

	file.writeline "<?xml version='1.0' encoding='utf-8'?>"
	file.writeline "<dish>"
	file.writeline "	<id>" + "dish" + cstr(id) + "</id>"
	file.writeline "	<name>" + name + "</name>"

	file.writeline "	<tags>"
	if ( id mod 2 = 0) then
		tag = "偶数的标签"
		price = 32.0
	else
		tag = "奇数的标签"
		price = 17.5
	end if
	file.writeline "		<tag>" + tag + "</tag>"
	if (id mod 10 = 0) then
		file.writeline "		<tag>整十的标签</tag>"
		price = 125
	end if
	file.writeline "		<tag>全部标签</tag>"
	file.writeline "	</tags>"

	file.writeline "	<price>" + cstr(price) + "</price>"
	file.writeline "	<score>" + cstr(score) + "</score>"
	file.writeline "	<summary>" +summary + "</summary>"
	file.writeline "	<detail>" + detail + "</detail>"
	file.writeline "	<thumb>" + thumb + "</thumb>"
	file.writeline "	<picture>" + picture + "</picture>"

	file.writeline "</dish>"

	file.close
end function

function generate_dish(index, imagePath, rootDir)
	pureName = get_file_pure_name(imagePath)
	fileName = get_file_name(imagePath)

	dishDir = rootDir + "\" + pureName
	call generate_dir(dishDir)

	set so=createobject("scripting.filesystemobject")
	so.copyfile imagePath, dishDir + "\" + fileName

	xml = dishDir + "\" + pureName + ".xml"

	call write_dish_xml(xml, index,	"带中文名称" + pureName, 32.5,	0.0, "这里是对于" + pureName + "的简短的说明", "这里是对于" + pureName + "的详细说明,在这里会有较多的文字描述.目前存在的问题是在windows系统下, 系统对于中文采用GB2312码, 这会对android平台应用程序对于从文件中读取的字符产生影响. 对文件编码的识别代价较大. 因此目前只能通过手动修改配置文件的办法指定编码集, 从而达到消除windows下中文xml文件读取字符出现的乱码.",	fileName, fileName)
end function

function main(sourceDir, targetDir)
	call generate_dir(targetDir)

	set fs = createobject("scripting.filesystemobject")
	on error resume next
	set parentFolder = fs.getfolder(sourceDir)

	set fileList = parentFolder.files

	i = 1
	for each file in fileList
		call generate_dish(i, file, targetDir)
		i = i + 1
	next
end function

call main("d:\image-test", "d:\dishes")
