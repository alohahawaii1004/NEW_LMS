/*
 * @(#)paragon.file.js     2023-02-14 014 오후 5:06:17
 *
 * Copyright 2023 JAYU.space
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

function ParagonFile() {
    "use strict";
}

ParagonFile.prototype = {
    fileInit: {},
    arrFileInfo: {},
    delFileInfo: []
};

var paragonFile = new ParagonFile();

(function (pf) {
    "use strict";

    var jFile = (function () {

        /**
         * 첨부사이즈 셋팅
         */
        var setFileSize = function (ctrlUuid) {

            var totalFileSize = 0;
            var arrFileList = pf.arrFileInfo[ctrlUuid].infos;
            $(arrFileList).each(function (i, o) {
                totalFileSize += Math.ceil(o.fileSize / 1024)
            });

            $("#file_size_" + ctrlUuid).text(totalFileSize);

        };

        /**
         * 파일 선택 삭제
         */
        var deleteFile = function (ctrlUuid, atchUid) {

            //-- 화면에서 지우기
            $("#" + atchUid, $("#ul_file_list_" + ctrlUuid)).remove();

            //-- 파일 객체에서 지우기
            var arrFileList = pf.arrFileInfo[ctrlUuid].files;
            var newFileList = [];
            $(arrFileList).each(function (i, o) {
                if (o.atchUid === atchUid) {
                    return true;
                }
                newFileList.push(o);
            });
            pf.arrFileInfo[ctrlUuid]["files"] = newFileList;

            //-- 파일 정보에서 지우기
            var arrFileinfo = pf.arrFileInfo[ctrlUuid].infos;
//	console.log(arrFileinfo);
            var newFileinfo = [];
            var isLink = false;
            $(arrFileinfo).each(function (i, o) {
                if (o.isNew === "link") {
                    // 링크파일은 입력대상에서만 제외한다.
                    isLink = true;
                }
                if (o.atchUid === atchUid) {
                    return true;
                }
                newFileinfo.push(o);
            });
            pf.arrFileInfo[ctrlUuid]["infos"] = newFileinfo;

            //-- 파일 삭제 저장 처리를 위해 삭제 atchUid 모음.
            if (!isLink) { // 링크파일은 입력대상에서만 제외한다.
                pf.delFileInfo.push(atchUid);
            }

            setNoticeView(ctrlUuid);
            setFileSize(ctrlUuid);

            $("#file_search_" + ctrlUuid).val("");
        };

        /**
         * 안내문구 보이기/숨기기
         */
        var setNoticeView = function (ctrlUuid) {
            //--안내문구 보이기/숨기기 처리
            if (pf.arrFileInfo.hasOwnProperty(ctrlUuid)) {
                if (pf.arrFileInfo[ctrlUuid].infos.length > 0) {
                    $("#file_notice_" + ctrlUuid).hide();
                } else {
                    $("#file_notice_" + ctrlUuid).show();
                }
            }
        };

        /**
         * 업로드 파일 목록 생성
         */
        var addFile = function (ctrlUuid, atchUid, fileName, fileSize, byteNm) {
            $("#file_Element_" + ctrlUuid);

            var html = "";
            html += "<li id='" + atchUid + "' class='file_Element_" + ctrlUuid + "' >";
            html += "		<input type='hidden' name='atchUid'  value='" + atchUid + "' >";
            html += "<i class='fa fa-times' style='color:red;' onclick='paragonFile.deleteFile(\"" + ctrlUuid + "\",\"" + atchUid + "\");'></i>&nbsp;" + fileName + " / " + fileSize + byteNm + " "
            html += "</li>"

            $('ul', '.file_Element_' + ctrlUuid).append(html);

        };

        /**
         * 파일 추가 로직
         */
        var fileMultiUpload = function (files, ctrlUuid) {

            var jsonInit = pf.fileInit[ctrlUuid];
            var maxFileSize = jsonInit["maxFileSize"];   //--최대첨부크기
            var maxFileCount = jsonInit.maxFileCount;  //--최대첨부갯수
            var fileFilter = jsonInit.fileFilter;    //--허용첨부파일
            var fileRFilter = jsonInit.fileRFilter;   //--불가첨부파일

            var arrFilter = fileFilter.toUpperCase().split(";");
            var arrRFilter = fileRFilter.toUpperCase().split(";");

            // 다중파일 등록
            if (files != null) {
                var fileCnt = files.length;
                if (pf.arrFileInfo[ctrlUuid].files) {
                    fileCnt += pf.arrFileInfo[ctrlUuid].files.length;
                }
                if (maxFileCount < fileCnt) { //-- 최대 허용 파일갯수 채크
                    alert(paragonCmm.getLang("M.첨부갯수_초과", "'" + maxFileCount + "'"));
                    return false;

                } else {

                    var arrFileInfos = [];	//-- 파일정보 Json 배열
                    var arrFileLists = [];  //-- 파일객체 배열
                    var fileTpCd = $("input:hidden[name='_attachFileTpCd']", "#fileArea_" + ctrlUuid).val();
                    var relUid = $("input:hidden[name='_attachFileRelUid']", "#fileArea_" + ctrlUuid).val();
                    var fileAllInfo = {};
                    //-- 기 입력된 정보 불러오기
                    if (pf.arrFileInfo.hasOwnProperty(ctrlUuid)) {
                        //-- 파일객체 불러오기
                        var arrfiles = pf.arrFileInfo[ctrlUuid].files;
                        $(arrfiles).each(function (j, o) {
                            arrFileLists.push(o);
                        });
                        //-- 파일정보 불러오기
                        var infos = pf.arrFileInfo[ctrlUuid].infos;
                        $(infos).each(function (j, o) {
                            arrFileInfos.push(o);
                        });

                    }

                    for (var i = 0; i < files.length; i++) {
                        // 파일 이름
                        var fileName = files[i].name;
                        var fileSize = files[i].size;
                        var fileNameArr = fileName.split("\.");
                        // 확장자
                        var ext = fileNameArr[fileNameArr.length - 1];
                        if (fileNameArr.length < 2) {
                            // 확장자 체크
                            alert(paragonCmm.getLang('M.등록_불가_확장자'));
                            break;
                        }
//		    	console.log("ext length:"+fileNameArr.length);
//		    	console.log("ext:"+ext);
//		    	console.log("ext.upper:"+ext.toUpperCase());
                        // 파일 사이즈(단위 :MB)
                        var fileSize = files[i].size / 1024;
                        var byteNm = "KB";
                        if (1024 < fileSize) {
                            fileSize = fileSize / 1024;
                            byteNm = "MB";
                        }
                        //--파일 사이즈 소수점 2자리까지
                        if (fileSize.toString().indexOf(".") > -1) {
                            if (fileSize.toString().split(".")[1].length > 2) {
                                fileSize = fileSize.toFixed(2);
                            }
                        }

                        //-- 허용불가 확장자 체크
                        if (arrRFilter != undefined && arrRFilter != "" && ($.inArray(ext.toUpperCase(), arrRFilter) >= 0)) {
                            // 확장자 체크
                            alert(paragonCmm.getLang('M.등록_불가_확장자'));
                            break;
                        }

                        //-- 허용가능 확장자 체크
                        if (arrFilter != undefined && arrFilter != "" && ($.inArray(ext.toUpperCase(), arrFilter) < 0)) {
                            // 확장자 체크
                            alert(paragonCmm.getLang('M.등록_불가_확장자'));
                            break;

                        }
                        var duple = false;
                        // 중복 체크
                        $(arrFileLists).each(function (j, o) {
                            if (o.name == files[i].name) {
                                console.log(o.name + ":" + files[i].name);
                                console.log("중복:" + fileName);
                                duple = true;
                                return false;
                            }
                        });
                        if (!duple) {
                            var atchUid = paragonCmm.getRandomUUID();

                            //-- 파일 정보 담기
                            var fileJson = {};
                            fileJson["isNew"] = "true";
                            fileJson["ctrlUuid"] = ctrlUuid;
                            fileJson["fileTpCd"] = fileTpCd;
                            fileJson["relUid"] = relUid;
                            fileJson["atchUid"] = atchUid;
//		    		fileJson["saveFileName"] = new Date().format("yyyyMMddhhmmss")+"."+Math.floor(Math.random() * 10) + 1 +"."+ i;

                            //새로 랜덤을 만들었습니다.
                            var array2 = new Uint32Array(10);
                            // if (navigator.sayswho.startsWith("IE")) {
                            //     window.msCrypto.getRandomValues(array);
                            // } else {
                            //     window.crypto.getRandomValues(array);
                            // }
                            crypto.getRandomValues(array2);
                            var ranNum = array2[1];
                            fileJson["saveFileName"] = paragonCmm.getDateFormat("yyyyMMddhhmmss") + "." + ranNum % 10 + 1 + "." + i;
                            fileJson["fileName"] = fileName;
                            fileJson["fileSize"] = files[i].size;

                            files[i]["atchUid"] = atchUid;

                            arrFileInfos.push(fileJson);
                            arrFileLists.push(files[i]);


                            addFile(ctrlUuid, atchUid, fileName, fileSize, byteNm);
                        }
                    }
                    fileAllInfo["files"] = arrFileLists;
                    fileAllInfo["infos"] = arrFileInfos;
                    //-- 추가된 파일 셋팅
                    pf.arrFileInfo[ctrlUuid] = fileAllInfo
                    setFileSize(ctrlUuid);
                }
            } else {
                alert("ERROR");
            }
            setNoticeView(ctrlUuid);
        };

        /**
         * 파일 이벤트 추가
         */
        var addFileEvent = function (ctrlUuid, jsonInit) {

            pf.fileInit[ctrlUuid] = jsonInit;
            if (jsonInit.hasOwnProperty("defaultFile")) {
                pf.arrFileInfo[ctrlUuid] = {"infos": jsonInit["defaultFile"]};
                setNoticeView(ctrlUuid);
                setFileSize(ctrlUuid);
            }
            //-- 파일 선택 버튼 이벤트 추가
            $("#file_search_" + ctrlUuid).change(function (e) {
                var files = e.originalEvent.target.files;
                //-- 파일 선택 했을때 파일 추가 하기
                fileMultiUpload(files, ctrlUuid);
            });

            //-- 파일 끌어다 놓기 이벤트 추가
            var obj = $(".file_Element_" + ctrlUuid);
            obj.off();
            obj.on('dragenter', function (e) {
                e.stopPropagation();
                e.preventDefault();
                $("DIV.file_Element_" + ctrlUuid).css('border', '2px solid #5272A0');
            });

            obj.on('dragleave', function (e) {
                e.stopPropagation();
                e.preventDefault();
                $("DIV.file_Element_" + ctrlUuid).css('border', '2px dotted #8296C2');
//         $(paragonFile).css('border', '2px dotted #8296C2');
            });

            obj.on('dragover', function (e) {
                e.stopPropagation();
                e.preventDefault();
            });

            obj.on('drop', function (e) {
                e.preventDefault();

                var files = e.originalEvent.dataTransfer.files;
                if (files.length < 1)
                    return;

                var targetObj = e.originalEvent.target;
                var ctrlUuid = $(targetObj).attr("class").replace("file_Element_", "");

                //-- 파일 드랍 했을때 파일 추가 하기
                fileMultiUpload(files, ctrlUuid);

                $("DIV.file_Element_" + ctrlUuid).css('border', '2px dotted #8296C2');

            });
        };

        return {
            addFileEvent: addFileEvent,
            addFile: addFile,
            setFileSize: setFileSize,
            setNoticeView: setNoticeView,
            deleteFile: deleteFile,
            fileMultiUpload: fileMultiUpload
        };
    })();
    pf.addFileEvent = jFile.addFileEvent;
    pf.addFile = jFile.addFile;
    pf.setFileSize = jFile.setFileSize;
    pf.setNoticeView = jFile.setNoticeView;
    pf.deleteFile = jFile.deleteFile;
    pf.fileMultiUpload = jFile.fileMultiUpload;

})(paragonFile);

