new Vue({
    el:"#app",
    data:{
        brandList:[],
        brand:{},
        page:1,
        pageSize:10,
        total:0,
        maxPage:9,
        selectChecked:[],
        searchObj:{},
        isSelectedAll:false
    },
    methods:{
        //品牌分页
        pageHandler:function (page) {
            this.page=page;
            var _this=this;
            axios.post("/brand/findPage.do?page="+this.page+"&rows="+this.pageSize,_this.searchObj).then(function (respones) {
                if(respones.data.errorCode!==0){
                    alert(respones.data.errorMsg);
                }else{
                    _this.brandList=respones.data.rows;
                    _this.brandList.forEach((value, index)=>{
                        if(typeof value.checked === 'undefined'){
                            _this.$set(value, 'checked',false);
                        }
                    });
                    _this.total=respones.data.total;
                }
            }).catch(function (reason) {
                alert(reason);
            });
        },
        //添加品牌
        addBrand:function () {
            var url;
            var _this=this;
            if(_this.brand.id==null){
                url="/brand/addBrand.do";
            }else {
                url="/brand/updateById.do";
            }
            axios.post(url,_this.brand).then(function (response) {
                if(response.data.errorCode!==0){
                    alert(response.data.msg);
                    _this.brand={};
                }else{
                    _this.pageHandler(_this.page);
                    alert("操作成功");
                    _this.brand={};
                }
            }).catch(function (reason) {
                alert(reason);
            })
        },

        //选中条数
        deleteSelection:function (event,id) {
            var _this=this;
            console.log("选中："+id);
            var checked=_this.brandList.indexOf(id);
            checked.checked=!checked.checked;
             if(event.target.checked){
                 _this.selectChecked.push(id);
             }else {
                 var idx=_this.selectChecked.indexOf(id);
                 _this.selectChecked.splice(idx);
             }
        },

        //删除选中
        deleteBrand:function () {
        var _this=this;
        axios.post("/brand/deleteBrand.do",Qs.stringify({ids:_this.selectChecked},{indices:false})).then(function (response) {
           if(response.data.errorCode!==0){
               alert(response.data.msg);
               _this.selectChecked=[];
               _this.pageHandler(_this.page);
           }else {
               _this.selectChecked=[];
               alert(response.data.msg);
               _this.pageHandler(_this.page);
           }
        }).catch(function (reason) {
            alert(reason);
        })
        },

        //根据id查出brand
        searchBrand:function (id) {
            var _this = this;
            axios.post("/brand/findById.do",Qs.stringify({brandId:id})).then(function (respones) {
                _this.brand =  respones.data.t;
            }).catch(function (reason) {
                alert(reason);
            })
        },
        //全选
        selectedAll(){
            // 3.1 总控制
            console.log('点击了全选');
            var _this=this;
            _this.isSelectedAll = !_this.isSelectedAll;
            // 3.2 遍历所有的商品数据
            this.brandList.forEach((value, index)=>{
                if(typeof value.checked === 'undefined'){
                    _this.$set(value, 'checked',true);
                }else {
                    value.checked = _this.isSelectedAll;
                }
                if(_this.isSelectedAll===true){
                    _this.selectChecked.push(value.id);
                }else {
                    _this.selectChecked=[];
                }
            });
        }
    },
    mounted:function () {
        this.pageHandler(this.page);
    }
});