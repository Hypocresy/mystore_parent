new Vue({
    el:'#app',
    data:{
        page:1,
        pageSize:10,
        total:0,
        maxPage:9,
        specification:{},
        specList:[],
        specIdArrqy:[],
        isAllSelect:false,
        searchObject:{},
        secEntity:{specificationOptionList:[],specification:{}}
    },
    methods:{
       pageHandler:function (page) {
           var _this = this;
           _this.page=page;
           axios.post("/spec/search.do?page=" + _this.page + "&rows=" + _this.pageSize, _this.searchObject).then(function (respones) {
               _this.specList = respones.data.rows;
               _this.specList.forEach((value, index) => {
                   if (typeof value.checked === 'undefined') {
                       _this.$set(value, 'checked', false);
                   }
               });
               _this.total=respones.data.total;
           }).catch(function (reason) {
               alert(reason);
           });
       },
        selected:function(event,item){
           var _this=this;
           item.checked=!item.checked;
           if(item.checked){
               _this.specIdArrqy.push(item.id);
           }else{
              var idx = _this.specIdArrqy.indexOf(item.id);
              _this.specIdArrqy.splice(idx);
           }
        },
        deleteSpec:function () {
            var _this=this;
            axios.post("/spec/deleteSpec.do",Qs.stringify(_this.specIdArrqy)).then(function (response) {
                if(respons.data.errorCode===0){
                    alert("操作成功");
                    _this.pageHandler(_this.page);
                }else{
                    alert("操作失败"+respons.data.errorMsg);
                    _this.pageHandler(_this.page);
                }
            }).catch(function (reason) {
                alert(reason);
            })
        },
        addAndUpdateSpec:function () {
            var _this= this;
            if(_this.secEntity.specification.specName===null){
                alert("修改信息不能为空");
                return;
            }
            _this.secEntity.specificationOptionList.forEach((value) =>{
                if(value.optionName==null||value.optionName=='undefined'){
                    alert("修改信息不能为空");
                    return;
                }
                if(value.orders==null||value=='undefined'){
                    alert("修改信息不能为空");
                    return;
                }
            } );
            axios.post("/spec/addAndUptateSpec.do",_this.secEntity).then(function (respons) {
                if(respons.data.errorCode===0){
                    alert("操作成功");
                    _this.specification={};
                    _this.pageHandler(_this.page);
                }else{
                    alert("操作失败"+respons.data.errorMsg);
                    _this.specification={};
                    _this.pageHandler(_this.page);
                }
            })
        },
        findSpecById:function (id) {
           var _this=this;
            axios.get("/spec/findSpecById.do?id="+id).then(function (respons) {
                if(respons.data.errorCode===0){
                   _this.secEntity.specification=respons.data.specification;
                   _this.secEntity.specificationOptionList =respons.data.specificationOptionList;
                }else {
                    console.log(respons.data.msg);
                }
            }).catch(function (reason) {
                alert(reason);
            })
        },
        deleteSpecOption:function (id) {
           console.log("id: "+id);
           var _this=this;
           var tmp=id;
           if(tmp==null||tmp=='undefined'){
               console.log("id1"+id);
               var lastIdx= _this.secEntity.specificationOptionList.length;
               _this.secEntity.specificationOptionList.splice(lastIdx-1);
           }else {
               axios.post("/spec/deleteSpecOption.do?id=" + id).then(function (response) {
                   if (response.data.errorCode === 0) {
                       console.log("操作成功！");
                   }
               });
               _this.findSpecById(_this.secEntity.specification.id);
           }
           alert("操作成功");

        },
        cancel:function () {
            var _this=this;
            _this.secEntity.specification={};
            _this.secEntity.specificationOptionList=[];
            _this.pageHandler(_this.page);
        },
        addOption:function () {
            var _this=this;
            var item={
                optionName:'',
                orders:null
            };
            _this.secEntity.specificationOptionList.push(item);
        }
    },
    mounted:function () {
        this.pageHandler(this.page);
    }
});