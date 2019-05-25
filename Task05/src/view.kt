package zipviewer

import java.util.zip.ZipEntry

fun getHelp() {
    println("Wrong arguments! Type one of the following commands:\n" +
            "    %path to archive% folder_info %folder_name%\n" +
            "    %path to archive% file_info %file_n%\n" +
            "    %path to archive% print_archive")
}

class View(private var model: Model) {

    fun printFolderList(folderName: String) {
        val folderList = model.getFolderList(folderName)

        if (folderList.isEmpty()) {
            println("There is no folder $folderName!")
            return
        }

        if (folderList.size == 1) {
            print("Folder $folderName exists!")
        }  else {
            println("There are ${folderList.size} folders with name $folderName:")

            for (folder in folderList) {
                val path = folder.name
                val size = model.getFolderSize(folder.name)
                println("    Path: $path\n    Size: $size bytes\n")
            }
        }
    }

    fun printFileList(fileName: String) {
        val fileList = model.getFileList(fileName)

        if (fileList.isEmpty()) {
            println("There is no file $fileName!")
            return
        }

        if (fileList.size == 1) {
            println("File $fileName exists!")
        } else {
            println("There are ${fileList.size} files with name $fileName:")
        }

        for (file in fileList) {
            val time = file.timeLocal.toString().substringBefore(".")
                       .replace("T", " ")

            println("    Path: ${file.name}\n    " +
                    "Time of creation: $time\n")
        }
    }

    fun printArchive() {
        val zipEntries = model.getEntries()

        if (zipEntries.isEmpty()) {
            println("Archive is empty!")
            return
        }

        for (entry in zipEntries) {
            var name = entry.name

            if (entry.isDirectory) name = name.dropLast(1)

            var cnt = name.filter {it in setOf('/')}.length
            name = name.substringAfterLast("/")
            var offset = if (cnt == 0) ""
                            else "||   ".repeat(cnt) + " ╚====="

            println(offset + name)
        }
    }
}
