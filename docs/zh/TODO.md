TODO
=====
* 功能/使用
	* 增加更多Event（以下建議）
		* GPS位置
		* 接收特定Broadcast
		* 收到特定Notification
	* 增加更多Operation（以下建議）
		* 定位
		* 音量
		* 發送TCP/UDP包
	* UI方面
		* 讓Event修改界面更好看
		* 動態添加Operation到Profile修改界面
		* 調整概覽頁面以更好看（使用卡片）
		* 調整Event頁面，使用樹形顯示
		* 完善*關於*界面
			* 增加Contributor於其上
			* 如果使用WebView，將返回的懸浮按鈕改爲WebView的上一頁；如果確定不使用，移除該懸浮按鈕
		* 增加運行時權限檢查
	* Event
		* 合併同類Event以降低耗電——通過減少監測器/`BroadcastReceiver`的數量
* 代碼/開發
	* 繪製/描述軟件結構，便於理解
	* 代碼中所有TODO
	* 測試的覆蓋率
	* 尋找方法將Plugin改爲單獨的apk，而非集成在Easer主體中
* 週遭
	* 更詳細的介紹

待定
=======
* 增加*智能Event添加*界面，使得可以直接選擇所有想要的Event並自動分析並添加成樹
* 將Event之間關係由樹改爲圖
