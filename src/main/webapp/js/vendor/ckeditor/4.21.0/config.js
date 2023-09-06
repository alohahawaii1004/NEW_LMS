/**
 * @license Copyright (c) 2003-2021, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
    // Define changes to default configuration here.
    // For complete reference see:
    // https://ckeditor.com/docs/ckeditor4/latest/api/CKEDITOR_config.html

    // The toolbar groups arrangement, optimized for a single toolbar row.
    // config.toolbarGroups = [
    //     // { name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
    //     // { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
    //     // { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
    //     // { name: 'forms' },
    //     { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
    //     { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
    //     { name: 'links' },
    //     { name: 'insert' },
    //     { name: 'styles' },
    //     { name: 'colors' },
    //     { name: 'tools' },
    //     { name: 'others' },
    //     { name: 'about' }
    // ];
    //
    // // The default plugins included in the basic setup define some buttons that
    // // are not needed in a basic editor. They are removed here.
    // config.removeButtons = 'Cut,Copy,Paste,Undo,Redo,Anchor,Underline,Strike,Subscript,Superscript';
    //
    // // Dialog windows are also simplified.
    // config.removeDialogTabs = 'link:advanced';

    config.uiColor = '#FAFAFA';
    config.enterMode = CKEDITOR.ENTER_DIV;
    config.toolbar =[
        ['PasteText','PasteFromWord','Bold','Italic','Underline','Strike', 'Subscript','Superscript'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['NumberedList','BulletedList','Outdent','Indent','Blockquote'],
        // ['HorizontalRule','Smiley','SpecialChar','Image','Table'],
        ['HorizontalRule','Smiley','SpecialChar','Table'],
        ['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize']
    ];

    config.font_names = '맑은 고딕; 바탕; 굴림; 돋움; 궁서; HY견고딕; HY견명조; 휴먼둥근헤드라인;'
        + '휴먼매직체; 휴먼모음T; 휴먼아미체; 휴먼엑스포; 휴먼옛체; 휴먼편지체;'
        +  CKEDITOR.config.font_names;
    config.removePlugins = 'magicline,elementspath';
    //config.forcePasteAsPlainText = true;

    config.pasteFromWordRemoveFontStyles = false;
    config.pasteFromWordRemoveStyles = false;
    config.allowedContent = true;

    // 이미지 추가 관련 플러그인
    // config.extraPlugins = 'popup,filetools,filebrowser,uploadimage,image2,tableresize,dialogadvtab,contextmenu';


    // var extraPluginSet = (function(){
    //     var exPlugins = [];
    //     var add = function(plugin){
    //         if(exPlugins.indexOf(plugin) === -1){
    //             exPlugins.push(plugin);
    //         }
    //     };
    //
    //     var toString = function(){
    //         var str = "";
    //         exPlugins.forEach(function(v, i){
    //             if(i !== 0){
    //                 str += ",";
    //             }
    //             str += v.toString();
    //         });
    //
    //         return str;
    //     };
    //     return {
    //         add: add,
    //         toString: toString
    //     };
    // })();
    //
    // // 추가 플러그인: file browser(의존성: popup, File Tools)
    // extraPluginSet.add("filebrowser");
    // // 추가 플러그인: popup
    // extraPluginSet.add("popup");
    // // 추가 플러그인: File Tools
    // extraPluginSet.add("filetools");
    //
    // // 추가 플러그인: uploadimage(의존성: uploadwidget)
    // extraPluginSet.add("uploadimage");
    // // 추가 플러그인: uploadwidget(의존성: File Tools, Notification Aggregator, Widget, Clipboard)
    // extraPluginSet.add("uploadwidget");
    // // 추가 플러그인: Notification Aggregator(의존성: Notification)
    // extraPluginSet.add("notificationaggregator");
    // // 추가 플러그인: notification
    // extraPluginSet.add("notification");
    // // 추가 플러그인: widget(의존성: line utils, Widget Selection, clipboard)
    // extraPluginSet.add("widget");
    // // 추가 플러그인: Line Utils
    // extraPluginSet.add("lineutils");
    // // 추가 플러그인: Widget Selection
    // extraPluginSet.add("widgetselection");
    // // 추가 플러그인: clipboard(의존성: Notification, dialog, Editor toolbar)
    // extraPluginSet.add("clipboard");
    // // 추가 플러그인: dialog( 의존성: dialogui)
    // extraPluginSet.add("dialog");
    // // 추가 플러그인: dialog user interface
    // extraPluginSet.add("dialogui");
    // // 추가 플러그인: Editor Toolbar(의존성: UI Button)
    // extraPluginSet.add("toolbar");
    // // 추가 플러그인: UI Button
    // extraPluginSet.add("button");
    //
    // // 추가 플러그인: Enhanced Image(의존성: widget, dialog)
    // extraPluginSet.add("image2");
    //
    // // 추가 플러그인: color dialog(의존성: dialog)
    // extraPluginSet.add("colordialog");
    //
    // // 추가 플러그인: table resize(의존성: table tools)
    // extraPluginSet.add("tableresize");
    //
    // // 추가 플러그인: table tools(의존성: table, dialog, context menu)
    // extraPluginSet.add("tabletools");
    //
    // // 추가 플러그인: table tools(의존성: dialog)
    // extraPluginSet.add("table");
    // // 추가 플러그인: Table Selection(의존성: clipboard, table tools)
    // extraPluginSet.add("tableselection");
    //
    // // 추가 플러그인: context menu(의존성: Menu)
    // extraPluginSet.add("contextmenu");
    //
    // // 추가 플러그인: Menu
    // extraPluginSet.add("menu");
    //
    // // 추가 플러그인: float panel(의존성: Panel)
    // extraPluginSet.add("floatpanel");
    //
    // // 추가 플러그인: panel
    // extraPluginSet.add("panel");
    //
    // // 추가 플러그인: Advanced Tab for Dialogs(의존성: dialog)
    // extraPluginSet.add("dialogadvtab");

    // // 추가 플러그인: Paste from Word(의존성: Paste Tools)
    // extraPluginSet.add("pastefromword");
    // // 추가 플러그인: Paste Tools(의존성: Clipboard, Ajax Data Loading)
    // extraPluginSet.add("pastetools");
    // // 추가 플러그인: Ajax Data Loading(의존성: XML)
    // extraPluginSet.add("ajax");
    // // 추가 플러그인: XML
    // extraPluginSet.add("xml");
    // // 추가 플러그인: Paste from Google Docs(의존성: Paste Tools)
    // extraPluginSet.add("pastefromgdocs");
    // // 추가 플러그인: Paste from LibreOffice(의존성: Paste Tools)
    // extraPluginSet.add("pastefromlibreoffice");

    // 아래 플러그인은 설치 되지 않았음
    // Media Embed(의존성: Media Embed Base)
    // extraPluginSet.add("embed");
    // // Media Embed Base(의존성: Dialog, Widget, Notification Aggregator)
    // extraPluginSet.add("embedbase");
    // // Semantic Media Embed(의존성: Media Embed Base)
    // extraPluginSet.add("embedsemantic");
    //
    //
    // var extraPlugins = extraPluginSet.toString();
    // console.debug("extraPlugins.........."+extraPlugins);
    // config.extraPlugins = extraPlugins;

    config.extraPlugins = 'pastefromword,pastetools,ajax,xml,pastefromgdocs,pastefromlibreoffice,tableselection,filebrowser,popup,filetools,uploadimage,uploadwidget,notificationaggregator,notification,widget,lineutils,widgetselection,clipboard,dialog,dialogui,toolbar,button,image2,colordialog,tableresize,tabletools,table,contextmenu,menu,floatpanel,panel,dialogadvtab';


    config.image2_alignClasses = [ 'image-left', 'image-center', 'image-right' ];
    config.image2_captionedClass = 'image-captioned';
    // config.image2_altRequired = true;

    config.filebrowserUploadUrl = '/editor/imageUpload/json?editor=ck';
    config.filebrowserBrowseUrl = '/editor/viewImg';

    // 브라우저(지원) 철자검사 사용: extraPlugins - contextmenu
    config.disableNativeSpellChecker = false;
};
