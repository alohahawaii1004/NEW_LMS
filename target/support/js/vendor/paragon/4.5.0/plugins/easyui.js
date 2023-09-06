/*
 * @(#)easyui.js     2020-11-10(010) 오전 7:29
 *
 * Copyright 2020 JAYU.space
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

// 콤보박스 삭제시 데이터 초기화
$.extend($.fn.combo.methods, {
    removeme:function(jq){
        return jq.each(function(){
            var state = $.data(this,'combo');
            if (state){
                state.panel.panel('destroy');
                state.combo.remove();
                $(this).removeClass('combo-f').show();
                $.removeData(this,'combo');
            }
        });
    }
});
// 콤보박스 삭제시 데이터 초기화
$.extend($.fn.combobox.methods, {
    removeme:function(jq){
        return jq.each(function(){
            var state = $.data(this,'combobox');
            if (state){
                $.removeData(this,'combobox');
                $(this).removeClass('combobox-f');
            }
            $(this).combo('removeme');
        });
    }
});
// 날짜박스 날짜 포맷 검사
$.extend($.fn.validatebox.defaults.rules, {
    validDate: {
        validator: function(value){
            var date = $.fn.datebox.defaults.parser(value);
            var s = $.fn.datebox.defaults.formatter(date);
            return s==value;
        },
        // validator: function(value, element){
        //     var date = $.fn.datebox.defaults.parser(value);
        //     var s = $.fn.datebox.defaults.formatter(date);
        //     if(s==value){
        //         return true;
        //     }else{
        //         var emp = [];
        //         emp = emp.concat(element);
        //         $(emp[0]).datebox('setValue', '');
        //         return false;
        //     }
        // },
        message: 'Please enter a valid date.'
    }
});
