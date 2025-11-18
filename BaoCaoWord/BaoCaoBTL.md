


BÁO CÁO MÔN HỌC KIỂM THỬ PHẦN MỀM
ĐỀ TÀI: TÌM HIỂU CÔNG CỤ KIỂM THỬ ĐƠN VỊ JUNIT

Giảng viên hướng dẫn	Lê Trung Kiên
Nhóm sinh viên thực hiện	
	
	
	
	
	
	




Hà Nội 09 – 2023


                                                        Mục lục
CHƯƠNG I – TỔNG QUAN VỀ KIỂM THỬ PHẦN MỀM	2
1.1 Giới thiệu về kiểm thử	2
1.1.1 Khái quát vai trò quản lý kiểm thử	2
1.1.2 Mục tiêu của kiểm thử phần mềm	2
1.2 Các khái niệm về kiểm thử phần mềm	3
1.3 Quy trình kiểm thử phần mềm	4
1.4 Mô hình chữ V	5
1.5 Các phương pháp kiểm thử	6
1.5.1 Kiểm thử tĩnh – Static testing	6
1.5.2 Kiểm thử động – Dynamic testing	6
1.6 Các chiến lược kiểm thử	7
1.6.1 Kiểm thử hộp đen – Black box testing	7
1.6.2 Kiểm thử hộp trắng – White box testing	8
1.6.3 Kiểm thử hộp xám – Gray box testing	9
1.7 Các cấp độ kiểm thử phần mềm	9
1.7.1 Kiểm thử đơn vị - Unit test	10
1.7.2 Kiểm thử tích hợp – Intergration Test	11
1.7.3 Kiểm thử hệ thống – System Test	12
1.7.4 Kiểm thử chấp nhận – Acceptance Test	14
CHƯƠNG II – KIỂM THỬ TỰ ĐỘNG VÀ KIỂM THỬ JUNIT	16
2.1 Kiểm thử đơn vị	16
2.2.1 Tiến trình kiểm thử	16
2.2.2 Kế hoạch kiểm thử Unit	17
2.2.3 Kế hoạch kiểm thử hộp đen – Black box	17
2.2.4 Kế hoạch kiểm thử hộp trắng – White box	18
2.3 Vòng đời Unit Testing	19
2.4 Lợi ích của Unit Testing	19
2.5 Công cụ kiểm thử JUnit	22
2.5.1 Khái niệm	22
2.5.2 Lợi ích của JUnit	23
2.5.3 Kiến trúc tổng quan	24
2.5.4 Các phương thức sử dụng phổ biến trong Junit	25
CHƯƠNG III – KIỂM THỬ CHƯƠNG TRÌNH	28
3.1 Xây dựng chương trình	28
3.1.1 Mô tả bài toán	28
3.1.2 Mô tả chương trình	28
3.1.3 Các yêu cầu chức năng	28
3.2 Chương trình	29
3.3 Hướng dẫn tạo các Unit test	33
KẾT LUẬN	39



LỜI MỞ ĐẦU
Trong ngành kỹ nghệ phần mềm, năm 1979, có một quy tắc nổi tiếng là: “Trong một dự án lập trình điển hình, thì xấp xỉ 50% thời gian và hơn 50% tổng chi phí được sử dụng trong kiểm thử các chương trình hay hệ thống đã được phát triển”. Và cho đến nay, sau gần một phần 3 thế kỷ, quy tắc đó vẫn còn đúng. Đã có rất nhiều ngôn ngữ, hệ thống phát triển mới với các công cụ tích hợp cho các lập trình viên sử dụng phát triển ngày càng linh động. Nhưng kiểm thử vẫn đóng vai trò hết sức quan trọng trong bất kỳ dự án phát triển phần mềm nào. 
Mục tiêu của kiểm thử phần mềm là thiết kế những trường hợp kiểm thử để có thể phát hiện một cách có hệ thống những loại lỗi khác nhau và thực hiện việc đó với lượng thời gian và tài nguyên ít nhất có thể. Trong đó có một nguyên lý được đúc kết từ thực tiễn: Kiểm thử đơn vị tìm ra khoảng 20% lỗi trong tất cả các lỗi của dự án, thời gian tốn cho kiểm thử đơn vị được đền bù bằng việc tiết kiểm rất nhiều thời gian và chi phí cho việc kiểm thử và sửa lỗi ở các mức kiểm thử sau đó.  
Vì vậy nhóm chúng em xin chọn đề tài “Kiểm thử phần mềm với công cụ Junit” để tìm hiểu thêm quy trình kiểm thử phần mềm cũng như công cụ kiểm thử tự động JUnit cho đề tài cuối kỳ.  
Mặc dù có nhiều cố gắng nhưng vốn kiến thức chưa sâu nên không thể tránh khỏi những thiếu sót. Rất mong được sự góp ý của quý thầy cô, anh chị cùng các bạn để nhóm chúng em hoàn thành báo cáo hoàn thiện hơn.


CHƯƠNG I – TỔNG QUAN VỀ KIỂM THỬ PHẦN MỀM
1.1 Giới thiệu về kiểm thử 
1.1.1 Khái quát vai trò quản lý kiểm thử 
Thuở ban đầu của nghành công nghiệp máy tính nói chung và công nghệ phần mềm nói riêng, việc phát triển phần mềm được xem là một tiến trình “viết, sửa”, không hề có một kế hoạch nào trước tiến trình này. Yêu cầu của một người dùng còn ở phạm vi nhỏ nên bản thân người viết phần mềm có thể tự nhớ được và kiểm tra để thỏa mãn yêu cầu người dùng. Nhưng hiện nay quy mô phần mềm rất lớn, cần nhiều người là chung, do đó kiểm thử phần mềm trở thành tiến trình quyết định sự thành công của dự án. 
Nếu ta tự quản lý tiến trình kiểm thử thì không đơn giản chút nào. Vì với số lượng trường hợp kiểm thử rất lớn, ta cần:  
Quản lý những trường hợp nào đã được thực hiện và những trường hợp kiểm thử nào chưa được thực hiện. 
Còn phải quản lý kết quả kiểm thử để thấy được khả năng đáp ứng yêu cầu của phần mềm. Kết quả này sẽ được thay đổi và phải được cập nhật lại sau mỗi phiên bản của sản phẩm. 
Yêu cầu người dùng sẽ còn được thay đổi trong suốt quá trình phát triển. Do đó các trường hợp kiểm thử liên quan đến những yêu cầu này cũng được thay đổi theo. Một số trường hợp kiểm thử sẽ được tạo mới, một số sẽ được cập nhật lại và được coi là phiên bản mới của trường hợp kiểm thử đó. 
Tất cả những thông tin này đều được phải ghi nhận lại. 
Do đó chúng ta có thể nói rằng một dự án muốn thành công không thể không có một chương trình quản lý kiểm thử.   
1.1.2 Mục tiêu của kiểm thử phần mềm 
Kiểm thử là một quá trình thực thi chương trình với mục đích là tìm ra lỗi và các điểm yếu của chương trình. 
Một trường hợp kiểm thử tốt nhất là một trường hơp có khả năng lớn trong việc tìm ra những lỗi chưa được phát hiện. 
Một trường hợp kiểm thử không tốt (không thành công) là một trường hợp mà khả năng tìm thấy những lỗi chưa biết đến là rất ít. 
Mục tiêu của kiểm thử phần mềm là thiết kế những trường hợp kiểm thử để có thể phát hiện một cách có hệ thống những loại lỗi khác nhau và thực hiện việc đó với lượng thời gian và tài nguyên ít nhất có thể. 
1.2 Các khái niệm về kiểm thử phần mềm 
Kiểm thử phầm mềm có nhiều định nghĩa khác nhau đề xuất bởi các tổ chức hay các cá nhân dưới đây là một số khái niệm tiêu biểu: 
Kiểm thử phần mềm là quá trình khảo sát một hệ thống hay thành phần dưới những điều kiện xác định, quan sát và ghi lại các kết quả và đánh giá một khía cạnh nào đó của hệ thống hay thành phần đó. (Theo Bảng chú giải thuật ngữ chuẩn IEEE của Thuật ngữ kỹ nghệ phần mềm- IEEE Standard Glossary of Software Engineering Terminology). 
Kiểm thử phần mềm là quá trình thực thi một chương trình với mục đích tìm lỗi. (Theo “The Art of Software Testing” – Nghệ thuật kiểm thử phần mềm). 
Kiểm thử phần mềm là hoạt động khảo sát thực tiễn sản phầm hay dịch vụ phần mềm trong đúng môi trường chúng dự định sẽ được triển khai nhằm cung cấp cho người dùng có lợi ích liên quan những thông tin về chất lượng của sản phẩm hay dịch vụ phần mềm ấy. Mục đihs của kiểm thử phần mềm là tìm ra các lỗi hay khiếm khuyết phần mềm nhằm đảo bảo hiệu quả hoạt động tối ưu của phần mềm trong nhiều nghành khác nhau. 
(Theo Bách khoa toàn thư mở Wikipedia). 
Có thể định nghĩa một cách dễ hiểu như sau: Kiểm thử phần mềm là một tiến trình hay một tập hợp các tiến trình được thiết kế để đảm bảo mã hóa các máy tính thực hiện theo cái mà chúng đã được thiết kế để làm, và không thực hiện bất cứ thứ gì không mong muốn. Đây là một pha quan trọng trong quá trình phát triển hệ thống, giúp cho người xây dựng hệ thống và khách hàng thấy được hệ thống mới đã đáp ứng yêu cầu đặt ra hay chưa. 
1.3 Quy trình kiểm thử phần mềm 
Tùy vào từng tổ chức, hệ thống, ngữ cảnh, mức độ rủi do của phần mềm mà qui trình kiểm thử phần mềm có thể gồm nhiều bước khác nhau. Nhưng nhìn chung mọi quy trình kiểm thử đều có những bước cơ bản như quy trình dưới đây: 
 
Một quy trình kiểm thử phần mềm cơ bản gồm 4 giai đoạn:
Lập kế hoạch kiểm thử
Nhiệm vụ quan trọng trong phần lập kế hoạch kiểm thử là xác định được các yếu tố sau: 
Các giai đoạn kiểm thử áp dụng cho dự án  
Các phương pháp kiểm thử  
Các công cụ kiểm thử  
Nguồn lực kiểm thử  
Tài nguyên môi trường kiểm thử, bao gồm các tài nguyên phần cứng và phần mềm   
Mốc bàn giao các tài liệu kiểm thử Chuẩn bị kiểm thử:  
Tìm hiểu nghiệp vụ của hệ thống phải kiểm thử  
Xây dựng kịch bản kiểm thử, phát triển các thủ tục và các kịch bản kiểm thử tự động (trong trường hợp kiểm thử tự động)  
Chuẩn bị dữ liệu kiểm thử  
Xem xét phê duyệt các tài liệu kiểm thử  
Thực thi kiểm thử:    
Thực hiện kiểm thử dựa trên các kịch bản kiểm thử,test script, thủ tục, dữ liệu có sẵn từ bước chuẩn bị kiểm thử.  
Tham gia quá trình quản lý lỗi: báo lỗi, sửa lỗi. 
Báo cáo và phân tích dữ liệu kiểm thử:    
Báo cáo kiểm thử   
Phân tích nguyên nhân và đề xuất các hành động khắc phục   
  Các kỹ thuật kiểm thử 
Có 3 kỹ thuật kiểm thử phần mềm chính là:   
Kiểm thử hộp đen  
Kiểm thử hộp trắng  
Kiểm thử hộp xám 
1.4 Mô hình chữ V
Kiểm thử và bảo trì là một pha quan trọng trong quá trình phát triển phần mềm

Hình 1.1 Mô hình phát triển chữ V
Bên trái chữ V là quá trình phát triển phần mềm, và bên phải là kiểm thử. Tại mỗi một mức trong quá trình phát triển thì có một pha kiểm thử tương ứng.  
Các mức kiểm thử có thể được lập kế hoạch và thiết kế song song. Sau đó chúng ta thực hiện kiểm thử từ đáy tháp chữ V nên tương ứng với từng mức phát triển. 
Kế hoạch kiểm thử hệ thống cần phải sớm hơn trước khi pha kiểm thử bắt đầu: 
Kế hoạch kiểm thử hệ thống là phải khớp với yêu cầu phần mềm. 
Các trường hợp kiểm thử cần phải hoàn thành khi mà các thiết kế chi tiết đã xong. 
Kiểm thử hệ thống bắt đầu từ ngay sau khi lập trình.
1.5 Các phương pháp kiểm thử 
Có 2 phương pháp kiểm thử chính: Kiểm thử tĩnh và kiểm thử động. 
1.5.1 Kiểm thử tĩnh – Static testing 
Là phương pháp kiểm thử phần mềm đòi hỏi phải duyệt lại các yêu cầu và các đặc tả bằng tay, thông qua việc sử dụng giấy, bút để kiểm tra logic, lần từng chi tiết mà không cần chạy chương trình. Kiểm thử này thường được sử dụng bởi chuyên viên thiết kế - người viết mã lệnh một mình. 
Kiểm thử tĩnh cũng có thể được tự động hóa. Nó sẽ thực hiện kiểm tra toàn bộ bao gồm các chương trình được phân tích bỏi một trình thông dịch hoặc biên dịch mà xác nhận tính hợp lệ về cú pháp của chương trình. 
1.5.2 Kiểm thử động – Dynamic testing 
Là phương pháp thử phần mềm thông qua việc dùng máy chạy chương trình để điều tra trạng thái tác động của chương trình. Đó là kiểm thử dựa trên các ca kiểm thử xác định bằng sự thực hiện của đối tượng kiểm thử hay chạy các chương trình. Kiểm thử tự động kiểm tra cách thức hoat động của mã lệnh, tức là kiểm tra sự phản ứng vật lý từ hệ thống tới các biên luôn thay đổi theo thời gian. Trong kiểm thử tự động, phần mềm phải thực sự được biên dịch và chạy. Kiểm thử động thực sự bao gồm làm việc với phần mềm, nhập các giá trị đầu vào và kiểm tra xem liệu đầu ra có như mong muốn hay không. Các phương pháp kiểm thử tự động gồm có Unit – Unit Tests, Kiểm thử tích hợp – Intergration Tests, Kiểm thử hệ thống – System Tests, và Kiểm thử chấp nhận sản phẩm – Acceptance Tests. 
1.6 Các chiến lược kiểm thử 
Ba trong số những chiến lược kiểm thử thông dụng nhất bao gồm: Kiểm thử hộp đen, Kiểm thử hộp trắng và Kiểm thử hộp xám. 
1.6.1 Kiểm thử hộp đen – Black box testing 
Một trong những chiến lược kiểm thử quan trọng là kiểm thử hộp đen, hướng dữ liệu, hay hướng vào/ ra. Kiểm thử hộp đen xem chương trình như là một “hộp đen”. Mục đích của bạn là hoàn toàn không quan tâm về cách cư xử và cấu bên trong của chương trình. Thay vào đó, tập trung vào tìm các trường hợp mà chương trình không thực hiện theo đặc tả của nó. 
Theo hướng tiếp cận này, dữ liệu kiểm tra được lấy chỉ từ các đặc tả. 
- Các phương pháp kiểm thử hộp đen:  
Kiểm thử lớp tương đương – Equivalence partitioning 
Kiểm thử giá trị biên – Boundary value analysis   Kiểm thử mọi cặp – All-pairs testing.  
Kiểm thử fuzz – Fuzz testing  
Kiểm thử dựa trên mô hình – Model-based testing. 
Ma trận dấu vết –Traceability matrix. 
Kiểm thử thăm dò – Exploratory testing. 
Kiểm thử dựa trên đặc tả - Specification –base testing. 
Kiểm thử dựa trên đặc tả tập trung vào kiểm tra tính thiết thực của phần mềm theo những yêu cầu thích hợp. Do đó, kiểm thử viên nhập dữ liệu vào, và chỉ thấy dữ liệu ra từ đối tượng kiểm thử. Mức kiểm thử này thường yêu cầu các ca kiểm thử triệt để được cung cấp cho kiểm thử viên mà khi đó có thể xác minh là đối với dữ liệu đầu vào đã cho, giá trị đầu ra (hay cách thức hoạt động) có giống với giá trị mong muốn đã được xác định trong ca kiểm thử đó hay không. Kiểm thử dựa trên đặc tả là cần thiết, nhưng không đủ để ngăn, chặn những rủi ro chắc chắn. 
- Ưu, nhược điểm.  
Kiểm thử hộp đen không có mối liên quan nào tới mã lệnh, và kiểm thử viên chỉ đơn giản tâm niệm là: Một mã lệnh phải có lỗi. Sử dụng nguyên tắc “Hãy đòi hỏi và bạn sẽ được nhận”, những kiểm thử viên hộp đen tìm ra lỗi mà những lập trình viên đã không tìm ra. Nhưng mặt khác, người ta cũng nói kiểm thử hộp đen “giống như là đi trong bóng tối mà không có đèn vậy”, bởi vì kiểm thử viên không biết các phần mềm được kiểm tra thực sự được xây dựng như thế nào. Đó là lý do mà có nhiều trường hợp một kiểm thử viên hộp đen viết rất nhiều ca kiểm thử để kiểm tra một thứ gì đó mà đáng lẽ có thể chỉ cần bằng một ca kiểm thử duy nhất, và/hoặc một số phần của chương trình không được kiểm tra chút nào. 
Do vậy, kiểm thử hộp đen có ưu điểm của “một sự đánh giá khách quan”, mặt khác nó lại có nhưng nhược điểm của “thăm dò mù”. 
1.6.2 Kiểm thử hộp trắng – White box testing 
Là một chiến lược kiểm thử khác, trái ngược hoàn toàn với kiểm thử hộp đen, kiểm thử hộp trắng hay kiểm thử hướng logic cho phép bạn khảo sát cấu trúc bên trong của chương trình. Chiến lược này xuất phát từ dữ liệu kiểm thử bằng sự kiểm thử tính logic của chương trình. Kiểm thử viên sẽ truy cập vào cấu trúc dữ liệu và giải thuật bên trong chương trình (và cả mã lệnh thực hiện chúng). 
- Các phương pháp kiểm thử hộp trắng 
Kiểm thử giao diện lập trình ứng dụng – API testing (application programming interface): là phương pháp kiểm thử của úng dụng sử dụng các API công khai và riêng tư. 
Bao phủ mã lệnh – Code voverage: tạo các kiểm tra để đáp ứng một số tiêu chuẩn về bao phủ mã lệnh. 
Các phương pháp gán lỗi – Fault injection.  
Các phương pháp kiểm thử hoán chuyển – MUnit testation testing methods. 
Kiểm thử tĩnh – Static testing: kiểm thử hộp trắng bao gồm mọi kiểm thử tĩnh. 
Phương pháp kiểm thử hộp trắng cũng có thể được sử dụng để đánh giá sự hoàn thành của một bộ kiểm thử mà được tạo ra cùng với các phương pháp kiểm thử hộp đen. Điều này cho phép các nhóm phần mềm khảo sát các phần của một hệ thống ít khi được kiểm tra và đảm bảo rằng những điểm chức năng quan trọng nhất đã được kiểm tra. 
1.6.3 Kiểm thử hộp xám – Gray box testing 
Kiểm thử hộp xám đòi hỏi phải có sự truy cập tới cấu trúc dữ liệu và giải thuật bên trong cho những mục đích thiết kế các ca kiểm thử, nhưng là kiểm thử ở mức người sử dụng hay mức hộp đen. Việc thao tác tới dữ liệu đầu vào và định dạng dữ liệu đầu ra là không rõ ràng, giống như một chiếc “hộp xám”, bởi vì đầu vào và đầu ra rõ ràng là ở bên ngoài “hộp đen” mà chúng ta vẫn gọi về hệ thống được kiểm tra. Sự khác biệt này đặc biệt quan trọng khi quản lý kiểm thử tích hợp – Intergartion testing giữa 2 module mã lệnh được viết bởi hai chuyên viên thiết kế khác nhau, trong đó chỉ giao diện được viết bởi hai chuyên viên thiết kế khác nhau, trong đó chỉ giao diện là được đưa ra để kiểm thử. Kiểm thử hộp xám có thể cũng bao gồm cả thiết kế đối chiếu để quyết định, ví dụ: giá trị biên hay thông báo lỗi. 	 
1.7 Các cấp độ kiểm thử phần mềm 
Kiểm thử phần mềm có các cấp độ: Kiểm thử đơn vị, kiểm thử tích hợp, kiểm thử hệ thống và kiểm thử chấp nhận sản phẩm 

Hình 1.2 Các cấp độ kiểm thử phần mềm
1.7.1 Kiểm thử đơn vị - Unit test 
Một đơn vị là một thành phần phần mềm nhỏ nhất mà ta có thể kiểm thử được. Ví dụ, các hàm (Function), thủ tục (Procedure), lớp (Class) hay phương thức (Method) đều có thể được xem là Unit. 
Vì Unit được chọn là kiểm tra thường có kích thước nhỏ và chức năng hoạt động đơn giản, chúng ta không khó khăn gì trong việc tổ chức kiểm thử, ghi nhận và phân tích kết quả kiểm thử. Nếu phát hiện lỗi, việc xác định nguyên nhân và khắc phục cũng tương đối dễ dàng chỉ khoanh vùng trong một đơn thể Unit đang kiểm tra. Một nguyên lý đúc kết từ thực tiễn: thời gian tốn cho Unit Test được đền bù bằng việc tiết kiểm rất nhiều thời gian và chi phí cho việc kiểm thử và sửa lỗi ở các mức kiểm thử sau đó. 
Unit test thường do lập trình viên thực hiện. Công đoạn này cần được thực hiện càng sớm càng tốt trong giai đoạn code và xuyên suốt chu kỳ phát triển phần mềm. Thông thường, Unit Test đòi hỏi kiểm thử viên có kiến thức về thiết kế và code của chương trình. Mục đích của Unit Test là đảm bảo thông tin được xử lý và xuất (khỏi Unit) là chính xác, trong mối tương quan với dữ liệu nhập và chức năng của Unit. Điều này thường đòi hỏi tất cae các nhánh bên trong Unit đều phải được kiểm tra để phát hiện nhánh phát sinh lỗi. Một nhánh thường là một chuỗi các lệnh được thực thi trong một Unit. Ví dụ: chuỗi các lệnh sau điều kiện If và nằm giữa then … else là một nhánh. Thực tế việc chọn lựa các nhánh để đơn giản hóa việc kiểm thử và quét hết Unit đòi hỏi phải có kỹ thuật, đôi khi phải dùng thuật toán để chọn lựa. 
Cùng với các mục tiêu kiểm thử khác, Unit Test cũng đòi hỏi phải chuẩn bị trước các ca kiểm thử (Test case) hoặc kịch bản kiểm thử (Test script), trong đó chỉ định rõ dữ liệu đầu vào, các bước thực hiện và dữ liệu đầu ra mong muốn. Các Test case và Test scrip này nên được giữ lại để tái sử dụng. 
1.7.2 Kiểm thử tích hợp – Intergration Test 
Integration test kết hợp các thành phần của một ứng dụng và kiểm thử như một ứng dụng đã hoàn thành. Trong khi Unit Test kiểm tra các thành phần và Unit riêng lẻ thì Integration Test kết hợp chúng lại với nhau và kiểm tra sự giao tiếp giữa chúng. 
Hai mục tiêu chính của Integration Test: 
Phát hiện lỗi giao tiếp xảy ra giữa các Unit. 
Tích hợp các Unit đơn lẻ thành các hệ thống nhỏ (Subsystem) và cuối cùng là nguyên hệ thống hoàn chỉnh (System) chuẩn bị cho kiểm thử ở mức hệ thống (System). 
Trong Unit Test, lập trình viên cố gắng phát hiện trên những Unit đã được kiểm tra cẩn thận trước đó bằng Unit Test, và tất cả các lỗi mức Unit đã được sửa chữa. Một số người hiểu sai rằng Unit một khi đã qua giai đoạn Unit Test với các giao tiếp giả lập thì không cần phải thực hiện Integration Test nữa. Thực tết về tích hợp giữa các Unit dẫn đến những tình huống hoàn toàn khác. Một chiến lược cần quan tâm trong Integration Test là nên tích hợp dần từng Unit. Một Unit tại một thời điểm tích hợp vào một nhóm các Unit khác đã tích hợp trước đó và đã hoàn tất các đợt Integration Test trước đó. Lúc này, ta chỉ cần kiểm thử giao tiếp của Unit mới them vào với hệ thống các Unit đã tích hợp trước đó, điều này sẽ làm cho số lượng ca kiểm thử giảm đi rất nhiều, và sai sót sẽ giảm đáng kể. 
Có 4 loại kiểm thử trong Integration Test: 
Kiểm thử cấu trúc (Structure Test): Tương tự White Box Test, kiểm thử cấu trúc nhằm bảo đảm các thành phần bên trong đó của một chương trình chạy đúng và chú trọng đến hoạt động của các thành phần cấu trúc nội tại của chương trình chẳng hạn các câu lệnh và nhánh bên trong. 
Kiểm thử chức năng ( Functional Test): Tương tự Black Box Test, kiểm thử chức năng chỉ chú trọng đến chức năng của chương trình, mà không quan tâm đến cấu trúc bên trong chỉ khảo sát chức năng của chương trình theo yêu cầu kỹ thuật. 
Kiểm thử hiệu năng (Performance Test): Kiểm thử việc vận hành của hệ thống. 
Kiểm thử khả năng chịu tải (Stress Test): Kiểm thử các giới hạn của hệ thống.  
1.7.3 Kiểm thử hệ thống – System Test 
Mục đích System Test là kiểm thử thiết kế và toàn bộ hệ thống (sau khi tích hợp) có thỏa mãn yêu cầu đặt ra hay không. 
System Test bắt đầu khi tất cả các bộ phận của phần mềm đã được tích hợp thành công. Thông thường loại kiểm thử này tốn rất nhiều công sức và thời gian. Trong nhiều trường hợp, việc kiểm thử đòi hỏi một số thiết bị phụ trợ, phần mềm hoặc phần cứng đặc thù, đặc biệt là các ứng dụng thời gian thực, hệ thống phân bố, hoặc hệ thống nhúng. Ở mức độ hệ thống, người kiểm thử cũng tìm kiếm các lỗi, nhưng trọng tâm là đánh giá về hoạt động, thao tác, sự tin cậy và các yêu cầu khác liên quan đến chất lượng của toàn hệ thống. 
Điểm khác nhau then chốt giữa Integration Test và System Test là System Test chú trọng các hành vi và lỗi trên toàn hệ thống, còn Integration Test chú trọng sự giao tiếp giữa các đơn thể hoặc đối tượng khi chúng làm việc cùng nhau. Thông thường ta phải thực hiện Unit Test và Integration Test để bảo đảm mọi Unit và sự tương tác giữa chúng hoạt động chính xác trước khi thực hiện System Test. 
Sau khi hoàn thành Integration Test, một hệ thống phần mềm đã được hình thành cùng với các thành phần đã được kiểm tra đầy đủ. Tại thời điểm này, lập trình viên hoặc kiểm thử viên bắt đầu kiểm thử phần mềm như một hệ thống hoàn chỉnh. 
Việc lập kế hoạch cho System Test nên bắt đầu từ giai đoạn hình thành và phân tích các yêu cầu.  
System Test kiểm thử cả các hành vi chức năng của phần mềm lẫn các yêu cầu về chất lượng như độ tin cậy, tính tiện lợi khi sử dụng, hiệu năng và bảo mật. Mức kiểm thử này đặc biệt thích hợp cho việc phát hiện lỗi giao tiếp với phần mềm hoặc phần cứng bên ngoài, chẳng hạn các lỗi "tắc nghẽn" (deadlock) hoặc chiếm dụng bộ nhớ. Sau giai đoạn System Test, phần mềm thường đã sẵn sàng cho khách hàng hoặc người dùng cuối cùng kiểm thử chấp nhận sản phẩm (Acceptance Test) hoặc dùng thử (Alpha/Beta Test). 
Đòi hỏi nhiều công sức, thời gian và tính chính xác, khách quan, System Test thường được thực hiện bởi một nhóm kiểm thử viên hoàn toàn độc lập với nhóm phát triển dự án. Bản thân System Test lại gồm nhiều loại kiểm thử khác nhau, phổ biến nhất gồm: 
Kiểm thử chức năng (Functional Test): Bảo đảm các hành vi của hệ thống thỏa mãn đúng yêu cầu thiết kế. 
Kiểm thử hiệu năng (Performance Test): Bảo đảm tối ưu việc phân bổ tài nguyên hệ thống (ví dụ bộ nhớ) nhằm đạt các chỉ tiêu như thời gian xử lý hay đáp ứng câu truy vấn... 
Kiểm thử khả năng chịu tải (Stress Test hay Load Test): Bảo đảm hệ thống vận hành đúng dưới áp lực cao (ví dụ nhiều người truy xuất cùng lúc). Stress Test tập trung vào các trạng thái tới hạn, các "điểm chết", các tình huống bất thường như đang giao dịch thì ngắt kết nối (xuất hiện nhiều trong kiểm tra thiết bị như POS, ATM...)... 
Kiểm thử cấu hình (Configuration Test). 
Kiểm thử bảo mật (Security Test): Bảo đảm tính toàn vẹn, bảo mật của dữ liệu và của hệ thống. 
Kiểm thử khả năng phục hồi (Recovery Test): Bảo đảm hệ thống có khả năng khôi phục trạng thái ổn định trước đó trong tình huống mất tài nguyên hoặc dữ liệu; đặc biệt quan trọng đối với các hệ thống giao dịch như ngân hàng trực tuyến... 
Nhìn từ quan điểm người dùng, các cấp độ kiểm thử trên rất quan trọng: 
Chúng bảo đảm hệ thống đủ khả năng làm việc trong môi trường thực. 
Lưu ý là không nhất thiết phải thực hiện tất cả các loại kiểm thử nêu trên. Tùy yêu cầu và đặc trưng của từng hệ thống, tuỳ khả năng và thời gian cho phép của dự án, khi lập kế hoạch, người Quản lý dự án sẽ quyết định áp dụng những loại kiểm thử nào. 
1.7.4 Kiểm thử chấp nhận – Acceptance Test 
Thông thường, sau giai đoạn System Test là Acceptance Test, được khách hàng thực hiện (hoặc ủy quyền cho một nhóm thứ ba thực hiện). Mục đích của Acceptance Test là để chứng minh phần mềm thỏa mãn tất cả yêu cầu của khách hàng và khách hàng chấp nhận sản phẩm (và trả tiền thanh toán hợp đồng). 
Acceptance Test có ý nghĩa hết sức quan trọng, mặc dù trong hầu hết mọi trường hợp, các phép kiểm thử của System Test và Acceptance Test gần như tương tự, nhưng bản chất và cách thức thực hiện lại rất khác biệt. 
Đối với những sản phẩm dành bán rộng rãi trên thị trường cho nhiều người sử dụng, thông thường sẽ thông qua hai loại kiểm thử gọi là kiểm thử Alpha – Alpha Test và kiểm thử Beta – Beta Test. Với Alpha Test, người dùng kiểm thử phần mềm ngay tại nơi phát triển phần mềm, lập trình viên sẽ ghi nhận các lỗi hoặc phản hồi, và lên kế hoạch sửa chữa. Với Beta Test, phần mềm sẽ được gửi tới cho người dùng để kiểm thử ngay trong môi trường thực, lỗi hoặc phản hồi cũng sẽ gửi ngược lại cho lập trình viên để sửa chữa. 
Thực tế cho thấy, nếu khách hàng không quan tâm và không tham gia vào quá trình phát triển phần mềm thì kết quả Acceptance Test sẽ sai lệch rất lớn, mặc dù phần mềm đã trải qua tất cả các kiểm thử trước đó. Sự sai lệch này liên quan đến việc hiểu sai yêu cầu cũng như sự mong chờ của khách hàng. Ví dụ đôi khi một phần mềm xuất sắc vượt qua các phép kiểm thử về chức năng thực hiện bởi nhóm thực hiện dự án, nhưng khách hàng khi kiểm thử sau cùng vẫn thất vọng vì bố cục màn hình nghèo nàn, thao tác không tự nhiên, không theo tập quán sử dụng của khách hàng v.v...  
Gắn liền với giai đoạn Acceptance Test thường là một nhóm những dịch vụ và tài liệu đi kèm, phổ biến như hướng dẫn cài đặt, sử dụng v.v... Tất cả tài liệu đi kèm phải được cập nhật và kiểm thử chặt chẽ. 


CHƯƠNG II – KIỂM THỬ JUNIT
2.1 Kiểm thử đơn vị
Kiểm thử đơn vị ứng dụng ở mức module. Khi thực hiện kiểm thử mức đơn vị thì công đoạn này là do người lập trình viên (Deverloper) hay kỹ sư kiểm thử(Test Enginieer). Vì khi xây dựng được một test case để có thể tìm ra được lỗi là nhiều nhất thì người thực hiện phải biết về ngôn ngữ lập trình, có khả năng đọc và hiểu các đoạn code. 
 Kiểm thử đơn vị là mức thấp nhất trong tiến trình kiểm thử, thường áp dụng phương pháp kiểm thử hộp trắng. 
Kết quả của kiểm thử Unit thường tìm ra khoảng 20% lỗi trong tất cả lỗi của dự án. 
2.2.1 Tiến trình kiểm thử 
+) Kế hoạch kiểm thử 
Lập kế hoạch cho kiểm thử khác nhau (như kiểm thử hệ thống, kiểm thử tích hợp, kiểm thử đơn vị). Quyết định xem đặc điểm nào cần phải kiểm thử. Các hướng tiếp cận để kiểm thử Unit. 
- Phương pháp phân tích kiểm thử. 
- Kĩ thuật kiểm thử (hộp đen hay hộp trắng). 
- Các công cụ dùng trong kiểm thử. 
+) Thiết kế kiểm thử 
- Tạo các trường hợp kiểm thử 
- Thiết kế các thủ tục kiểm thử: 
- Thủ tục làm thế nào để thực thi một trường hợp kiểm thử 
- Một thủ tục có thể áp dụng cho một vài trường hợp kiểm thử khác 
Triển khai chương trình kiểm thử 
- Kiểm thử gốc (stub): Kiểm thửu lần lượt từ gốc của chương trình, sau khi xong thì tiếp tục kiểm thử Stub tiếp theo ở bên dưới 
- Kiểm thử driver: Driver là một trình điều khiển kiểm thử unit. 
+) Thực hiện và đánh giá kiểm thử Unit - Chuẩn bị kiểm thử môi trường. 
- Thực hiện kiểm thử Unit. 
- Phát hiện ra lỗi trong kiểm thử unit. 
- Làm báo cáo ghi lại toàn bộ sự thành công hay thất bại trong từng unit một dựa theo các kết quả yêu cầu. 
2.2.2 Kế hoạch kiểm thử Unit 
Để thực hiện một kiểm thử có hiệu quả, thì cần thiết phải có một kế hoạch kiểm thử có hiệu quả. Cần phải lập kế hoạc thật chi tiết, càng chi tiết càng tốt. 
Kế hoạch kiểm thử unit cần phải đưa ra các tài liệu chỉ dẫn thực hiện kiểm thử trên từng module như thế nào. Mục tiêu là mỗi module sau khi được kiểm thử thì phải thỏa mãn tất các yêu cầu đặt ra về chức năng. 
Kế hoạch kiểm thử cần phải đưa ra một danh sách các đầu vào cho module và một danh sách các đầu ra phù hợp với các module đó. Một module được gọi là đạt nếu tất cả các đầu vào đều có đầu ra tương ứng. Mỗi một sự sai lệch nào của đầu ra đều phải cần xem xét cụ thể. Danh sách các đầu vào phải thỏa mãn yêu cầu của phần mềm, tối thiểu là lần đầu tiên. Kế hoạch kiểm thử giúp cho các nhà phát triển có thể đảm bảo chắc chắn rằng mỗi dòng và mỗi câu lệnh điều kiện đều phải thực hiện được tối thiểu một lần. 
2.2.3 Kế hoạch kiểm thử hộp đen – Black box 
Là phương pháp tập trung vào yêu cầu về mặt chức năng của phần mềm. Có thể tạo ra một bộ các điều kiện các inpUnit test để kiểm thử tất cả các chức năng của một chương trình. Kiểm thử hộp đen về bản chất không phải là một phương pháp trái ngược với kiểm thử hộp trắng. Đúng hơn đây là phương pháp bổ sung cho phương pháp kiểm thử hộp trắng để phát hiện tất cả các loại lỗi khác nhau nhiều hơn là phương pháp kiểm thử hộp trắng đã biết. 
Kiểm thử hộp đen cố gắng phát hiện ra các lỗi như sau: 
- Không đúng hay mất một số hàm/module. 
- Giao diện không phù hợp/ lỗi về interface. 
- Lỗi về cấu trúc dữ liệu hay thao tác lên data bên ngoài. 
- Lỗi thực thi.
- Lỗi về khởi động và hủy dữ liệu. 
Không giống như phương pháp kiểm thử hộp trắng có thể được thực hiện ở những giai đoạn đầu của quá trình kiểm thử phần mềm, phương pháp này tập trung vào phần sau của quá trình kiểm thử. Mục đích của quá trình kiểm thử là tập trung trên vùng thông tin chứ không không phải trên vũng mã chương trình.  
2.2.4 Kế hoạch kiểm thử hộp trắng – White box 
Trong kiểm thử hộp trắng , các trường hợp kiểm thử được thiết kế để xem xét trên cấu trúc nội bộ của module và cấu trúc logic và cấu trúc điều khiển. Các trường hợp kiểm thử sẽ duyệt qua tất cả các lệnh trong chương trình. Tuy nhiên điều này cũng gặp các khó khăn như trình bày ở trên bởi số lượng công việc phải làm. Vậy tại sao ta không tập trung vào chi tiết thiết kế các trường hợp kiểm thử dựa trên kỹ thuật kiểm thử hộp đen. Câu trả lời nằm trong những yếu điểm tự nhiên của phần mềm: 
Những lỗi về lý luận và những giả sử không chính xác có xác suất xảy ra tương đương với những trường hợp đúng. Những lỗi có khuynh hướng xuất hiện khi chúng ta thiết kế và cài đặt chương trình, các biểu thức điều kiện, hoặc các biểu thức điều khiển, và các lỗi thường có khuynh hướng xuất hiện ở các trường hợp đặc biệt. 
Chúng ta thường tin rằng một đường biểu diễn tiến  trình nào đó sẽ không được thực thi. Tuy nhiên thực tế thì nó có thể thực thi. Luồng biểu diễn tiến trình của chương trình đôi khi chỉ mang tính trực giac, có thể hiểu là một giả định tưởng tượng của người lập trình về luồng điều khiển và dữ liệu đã làm cho chúng ta tạo lỗi. Lỗi loại này có thể được phát hiện bằng một trường hợp kiểm thử trên một đường biểu diễn tiến trình. 
Những lỗi về cài đặt sai do lỗi gõ phím ngẫu nhiên và có thể xuất hiện tại bất kỳ đâu trong chương trình. Khi một chương trình được chuyển đổi từ ý tưởng thiết kế sang thành mã chương trình một số lỗi do đánh sai hiểu sai xuất hiện. Phần lớn có thể được phát hiện bởi những hệ thống kiểm tra cú pháp của ngôn ngữ, nhưng một số khác sẽ không được phát hiện cho đến khi chạy kiểm thử. 
Mỗi một lý do giải thích tại sao phải tạo ra các trường hợp kiểm thử dựa trên những kỹ thuật hộp trắng. Hộp đen cũng được nhưng có thể một số loại lỗi ở trên sẽ không được phát hiện bởi các trường hợp sử dụng phương pháp này. 
2.3 Vòng đời Unit Testing 
Unit Testing có 3 trạng thái cơ bản: 
- Fail status (trạng thái lỗi) 
- Ignore status (tạm ngừng hiện thực) 
- Pass status (trạng thái làm việc) 
Toàn bộ Unit testing được vận hành trong một hệ thống tách biệt. Có rất nhiều phần mềm hỗ trợ thực thi Unit testing với giao diện trực quan. Thông thường, trạng thái của Unit được biểu hiện bằng các màu khác nhau: màu xanh (pass), màu đỏ (fail), màu vàng (ignore). 
Unit testing chỉ thực sự đem lại hiệu quả khi: 
- Được vận hành lặp lại nhiều lần 
- Tự động hoàn toàn 
- Độc lập với các Unit testing khác 
2.4 Lợi ích của Unit Testing 
Thời gian đầu, người ta thường do dự khi phải viết Unit test thay bì tập trung vào biết mã cho các chức năng nghiệp vụ. Công việc viết Unit test có thể ngốn nhiều thời gian, tuy nhiên Unit test đem lại lọi ích to lớn như: 
- Tạo ra môi trường lý tưởng để kiểm tra bất kỳ đoạn mã nào, có khả năng thăm dò và phát hiện lỗi chính xác, duy trì sự ổn định của toàn bộ phần mềm và giúp tiết kiệm thời gian so với công việc gỡ rối truyền thống. 
- Phát hiện các thuật toán thực thi không hiệu quả, các thủ tục chạy vượt quá giới hạn thời gian. 
- Phát hiện các vấn đề về thiết kế, xử lý hệ thống, thậm chí các mô hình thiết kế. 
- Phát hiện các lỗi nghiêm trọng có thể xảy ra trong những tình huống rất hẹp 
- Tạo hàng rào an toàn cho các khối mã: Bất kỳ sự thay đổi nào cũng có thể tác động đến hàng rào này và thông báo những nguy hiểm tiềm tàng. 
Unit test là môi trường lý tưởng để tiếp cận các thư viện API bên ngoài một cách tốt nhất. Sẽ rất nguy hiểm nếu chúng ta ứng dụng ngay các thư viện này mà không kiểm tra kỹ lưỡng công dụng của cac thủ tục trong thư viện. Dành ra thời gian viết Unit test kiểm tra từng thủ tục là phương pháp tốt nhất để khẳng định sự hiểu đúng đắn về cách sử dụng thư viện đó. Ngoài ra, Unit test cũng được sử dụng để phát hiện sự khác biệt giữa phiên bản mới và phiên bản cũ cùng một thư viện. 
+) Trong môi trường làm việc cạnh tranh, Unit test còn có tác dụng rất lớn đến năng suất làm việc: 
- Giải phòng chuyên viên QA (Quality Assurance) khỏi các công việc kiểm tra phức tạp. 
- Tăng sự tự tin hoàn thành một công việc. Chúng ta thường có cảm giác không chắc chắn về các đoạn mã của mình như liệu các lỗi có quay lại không, hoạt động module hiện hành có bị tác động không, hoặc liệu công việc hiệu chỉnh mã có gây hư hỏng đâu đó… 
- Là công cụ đánh giá năng lực của bạn. Số lượng các tình huống kiểm tra (test case) chuyển trang thái “pass” sẽ thể hiện tốc độ làm việc, năng suất của bạn. 
+) Chiến lược viết mã hiệu quả với Unit test: 
- Phân tích các tình huống có thể xảy ra đối với mã. Đừng bỏ qua các tình huống tồi tệ nhất có thể xảy ra, thí dụ dữ liệu nhập làm một kết nối cơ sở dữ liệu thất bại, ứng dụng bị treo vì một phép toán chi cho không, các thủ tục đưa ra lỗi ngoại lệ sai có thể phá hỏng ứng dụng một cách bí ẩn … 
- Mọi Unit test phải bắt đầu với trạng thái “fail” và chuyển trạng thái “pass” sau một số thay đổi không thể đổi hợp lý đối với mã chính. 
- Mỗi khi viêt một đoạn mã quan trọng, hãy viế các Unit test tương ứng cho đến khi bạn không thể nghĩ thêm tình huống nào nữa. 
- Nhập một số lượng đủ lớn các giá trị đầu vào để phát hiện điểm yếu của mã theo nguyên tắc: 
+ Nếu nhập giá trị đầu vào hợp lệ thì kết quả trả về cũng phải hợp lệ. 
+ Nếu nhập giá trị đầu vào không hợp lệ thì kết quả trả về cũng không hợp lệ. 
- Sớm nhận biết các đoạn mã không ổn định và có nguy cơ gây lỗi cao, viết Unit test tương ứng để khống chế. 
- Ứng với mỗi đối tượng nghiệp vụ hoặc đối tượng truy cập dữ liệu, nên tạo ra một lớp kiểm tra riêng vì những lỗi nghiêm trọng có thẻ phát sinh từ các đối tượng này. 
- Để ngăn chặn các lỗi có thể phát sinh trở lại thực thi tựu động tất cả Unit test mỗi khi có sự thay đổi quan trọng, hãy làm công việc này mỗi ngày. Các Unit test lỗi cho chúng ta biết thay đổi nào nguyên nhân gây lỗi. 
- Để tăng hiệu quả và giảm rủi ro khi viết các Unit test, cần sử dụng nhiều phương thức kiểm tra khác nhau. Hãy viết càng đơn giản càng tốt. 
Cuối cũng, viết Unit test đòi hỏi nỗ lực, kinh nghiệm và sự sáng tạo như viết phần mềm. 
Trước khi kết thúc phần này, chúng emcó một khuyên là viết Unit test cũng tương tự như viết mã chương trình, điều bạn cần làm là không ngừng thực hành, Hãy nhớ Unit test chỉ thực sự mang lại lợi ich nếu chúng ta đặt vấn đề chất lượng phần mềm lên hàng đầu hơn là chỉ nhằm kết thúc công việc đúng thời hạn. 
2.5 Công cụ kiểm thử JUnit 

2.5.1 Khái niệm 
JUnit là một framework đơn giản dùng cho việc tạo các unit testing tự động, chạy các test có thể lặp đi lặp lại và xác nhận kết quả kiểm tra một cách tự động. Nó chỉ là một phần của họ kiến trúc xUnit cho việc tạo các unit testing. JUnit là một chuẩn trên thực tế cho unit testing trong Java. 
JUnit được xây dựng vào năm 1997 bởi 2 tác giả Erich Gamma và Kent Beck – hai người nổi tiếng nhất về lập trình XP.  
JUnit là một phần mềm mã nguồn mở, được phát hành bởi giấy phép IBM’s Common Public License Version 1.0 và được lưu trữ trên Source Forge.  
Trong JUnit có các Testcase là các lớp của Java, các lớp này bao gồm một hay nhiều phương thức cần kiểm tra và testcase này lại được nhóm với nhau để tạo thành Test Suite.  
+) JUnit có các đặc điểm lưu tâm như sau: 
- Xác nhận (assert) việc kiểm tra kết quả được mong đợi.  
- Các test suite cho phép chúng ta dễ dàng tổ chức và chạy các test. 
- Hỗ trợ giao diện đồ họa và giao diện dòng lệnh. 
Các test case java là các lớp của java , các lớp này bao gồm 1 hay nhiều phương thức JUnit testing và những test này chạy được các nhóm thành các test suite cho phép chúng ta dễ dàng tổ chức và chạy các test. 
Mỗi phương thức test trong JUnit phải được thực thi nhanh chóng. Tốc độ là điều tối quan trọng vì càng nhiều test được viết và tích hợp vào bên trong quá trình xây dựng phần mềm, cần phải tốn nhiều thời gian hơn cho việc chạy toàn bộ Test Suite. Các lập trình viên không muốn bị ngắt quãng trong một khoãng thời gian dài trong khi các test chạy, vì thế các test mà chạy càng lâu thì sẽ có nhiều khả năng là các lập trình viên sẽ bỏ qua bước cũng không kém phần quan trọng này. 
Các test trong JUnit có thể là các test được chấp nhận hay thất bại, các test này được thiết kế để khi chạy mà không cần có sự can thiệp của con người. Từ những thiết kế như thế, bạn có thể thêm các bộ test vào quá trình tích hợp và xây dựng phần mềm một cách liên tục và để cho các test chạy một cách tự động 
2.5.2 Lợi ích của JUnit 
JUnit tránh cho người lập trình phải làm đi làm lại những việc kiểm thử nhàm chán bằng cách tách biệt mã kiểm thử ra khỏi mã chương trình, đồng thời tự động hóa việc tổ chức và thi hành các bộ số liệu kiểm thử. 
- Nếu không sử dụng JUnit, ta có thể soạn nhanh bộ số liệu rồi viết ngay vào trong phương thức main() và quan sát ngay kết quả kiểm thử. Vì quá trình soạn số liệu và quá trình kiểm thử diễn ra đồng thời, nên ta sẽ dễ dàng nhận biết được ngay chương trình đã chạy đúng trên bộ số liệu kiểm thử hay không, mà không cần nhìn vào tín hiệu “xanh” mà JUnit có thể hỗ trợ. 
- Tuy nhiên, khi chúng ta muốn tổ chức lại chương trinhg cho hợp lý hơn hoặc phải thay đổi chương trình để phục vụ cho nhu cầu mới, các bộ số liệu kiểm thử trước đây sẽ cần được sử dụng lại để chắc chắn rằng những thay đổi trong chương trình không làm phương hại đến những thành quả trước đó, lúc này ta sẽ phải mất thời gian để tìm hiểu lại xem bộ số liệu trước đây sẽ tương ứng với kết xuất gì vì ta không thể nhớ hết mọi hoạt động kiểm thử đã diễn ra. Nếu phải kiểm thử trên những bộ số liệu lớn thì gánh nặng của việc tổ chức kiểm thử sẽ chồng chất thêm. 
 JUnit giúp người lập trình tự động hóa các công việc nhàm chán trên, và chỉ cần nhìn thấy tín hiệu “xanh” là người lập trình có thể an tâm rằng module đã được lập trình đúng. 
2.5.3 Kiến trúc tổng quan 
 
Hình 2.1 Sơ đồ Kiến trúc tổng quát
- JUnit test framework cung cấp cho chúng ta các gói lớp có sẵn cho phép chúng ta viết các phương thức test một cách dễ dàng. 
- TestRunner sẽ chạy các test và trả về kết quả là các Test Results. 
- Các lớp của chương trình test chúng ta sẽ được kế thừa các lớp trừu tượng TestCase. 
- Khi viết các Test Case chúng ta cần biết và hiểu lớp Assert class. 
+) Một số định nghĩa trong mô hình tổng quát: 
- Test case : test case định nghĩa môi trường mà nó có thể sử dụng để chạy 
nhiều test khác nhau.
- TestSuite : testsuite là chạy một tập các test case và nó cũng có thể bao 
gồm nhiều test suite khác, test suite chính là tổ hợp các test. 
2.5.4 Các phương thức sử dụng phổ biến trong Junit 
Lớp Assertxxx() 
- Các phương thức dạng asserxxx() được dùng để kiểm tra các điều kiện khác nhau. 
- Trong lớp JUnit.framework.Assert, ta có các phương thức assertxxx() khác nhau sau: 
a) assertEquals() 
assertEquals([String message] , expected , actual) Trong đó:  
Expected: là giá trị bạn mong muốn đạt được. 
Actual: là giá trị thực được sinh ra bởi đoạn code cần test. 
So sánh hai giá trị expected và actual để kiểm tra bằng nhau. Phép thử thất bại nếu hai giá trị không bằng nhau. 
b) Boolean assertFalse()  
assertFalse([String message], boolean condition) 
Đánh giá biểu thức logic. Phép thử thất bại nếu biểu thức đúng. c. Boolean assertNotNull() 
assertNotNull([String message], java.lang.Object object) 
So sánh tham chiếu của một đối tượng với giá trị Null. Phép thử thất bại nếu tham chiếu đối tượng Null. 
c) Boolean assertNotSame() 
assertNotSame([String message], expected, actual) Trong đó: 
Expected: là giá trị bạn mong muốn đạt được. Actual: là giá trị thực được sinh ra bởi đoạn code cần test. 
d) Boolean assertNull() 
assertNull([String message], java.lang.Object object) 
So sánh tham chiếu của một đối tượng với giá trị Null. Phép thử thất bại nếu đối tượng không là Null. 
e) Boolean assertSame() 
assertSame([String message], expected, actual) Trong đó: 
Expected: là giá trị bạn mong muốn đạt được. 
Actual: là giá trị thực được sinh ra bởi đoạn code cần test. 
So sánh địa chỉ vùng nhớ của hai tham chiếu đối tượng expected và actual bằng cách sử dụng toán tử ==. Phép thử thất bại nếu cả hai không tham chiếu đến cũng một đối tượng. 
f) Boolean assertTrue() 
assertTrue([String message], boolean condition) 
Đánh giá một biểu thức logic. Phép thử thất bại nếu biểu thức sai. h. Void fail() 
Phương thức này làm cho test hiện hành thất bại. Phương thức này thường được sử dụng khi xử lý các ngoại lệ. Ví dụ như chúng ta sử dụng cặp từ khóa try/catch để bắt các exception như mong đợi, chúng ta sẽ gọi phương thức fail() khi exception chúng ta mong đợi không xảy ra. 
- Các phương thức test hoạt động bằng cách hội những phương thức này. 
- Mặc dù bạn có thể chỉ cần sử dụng phương thức assertTrue() cho hầu hết các test, tuy nhiên thì việc sử dụng một trong các phương thức assertxxx() cụ thể sẽ làm cho các test của bạn dễ hiểu hơn và cung cấp và thông điệp thất bại rõ ràng hơn. - Tất cả các phương thức của bảng trên đều nhận vào một String không bắt buộc làm tham số đầu tiên. Khi được xác định, tham số ày cung câp một thông điệp mô tả test thất bại. 
Setup() và Teardown() 
- Hai phương thức này là một phần của junit.framwork.Testcase. 
- Setup() là phương thức tạo ra các đối tượng và dữ liệu cần kiểm tra. 
tearDown() là phương thức để hủy bỏ chúng. 
- Khi sử dụng hai phương thức này sẽ giúp chúng ta tránh được việc trùng mã khi nhiều test cũng chia sẻ nhau ở phần khởi tạo và dọn dẹp các biến. 
- JUnit tuân thủ theo một dãy có thứ tự các sự kiện khi chạy các test. Đầu tiên, nó tạo ra một thể hiện mới của test case ứng với mỗi phương thức thử. Từ đó, nếu bạn có 5 phương thức thử thì JUnit sẽ tạo ra 5 test case. Vì lý do đó, các biến thể hiện không thể được sử dụng để chia sẻ trạng thái giữa các phương thức test. Sau khi tạo xong tất cả các đối tượng test case, JUnit tuân theo các bước sau cho mỗi phương thức test: 
+ Gọi phương thức Setup() của Test case 
+ Gọi phương thức thử 
+ Gọi phương thức TearDown() của Test case 
Quá trình này được lạo lại đối với mỗi phương thức thử trong Test case 
- Thông thường chúng ta có thể bỏ qua phương thức TearDown() vì mỗi phương thức thử riêng không phải là những tiến trình chạy tốn nhiều thời gian và các đối tượng đượ thu dọn khi máy ảo Java thoát. 
- Phương thức TearDown() có thể được sử dụng khi test của bạn thực hiện những thao tác như mở kết nối đến cơ sở dữ liệu hay sử dụng các loại tài nguyên khác của hệ thống và bạn cần phải dọn dẹp ngay lập tức. 
- Nếu bạn chạy một bôi bao gồm một số lượng lớn các unit test thì khi chúng ta trỏ tham chiếu của các đối tượng đến null bên trong thân phương thức TearDown() sẽ giúp cho trọn bộ dọn rác lấy lại bộ nhớ khi các test khác chạy. 



CHƯƠNG III – KIỂM THỬ CHƯƠNG TRÌNH
3.1 Xây dựng chương trình
3.1.1 Mô tả bài toán
- Mục đích: Xây dựng chương trình kiểm tra 3 cạnh có tạo thành một tam giác không, nếu có thì là loại tam giác gì và sử dụng JUnit để kiểm thử. Kiểm tra chương trình đúng hay sai. 
Phạm vi bài toán:  
- Dự án nằm trong khuôn khổ báo cáo bài thi cuối kỳ, nhằm mục đích tìm hiểu làm quen với kiểm thử phần mềm, kiểm thử đơn vị, công cụ JUnit và ngôn ngữ lập trình Java. 
- Sản phẩn kết quả của dự án là chương trình kiểm tra tam giác nhằm phục vụ cho việc sử dụng công cụ JUnit để tiến hành kiểm thử. 
- Các dữ liệu của chương trình bao gồm:  
Bài toán 1: Đầu vào của chương trình là độ dài các cạnh a, b và c của tam 
giác. Đầu ra của chương trình sẽ đưa ra đó là loại tam giác gì. 
3.1.2 Mô tả chương trình
- Tổng quan chương trình: Chương trình kiểm tra tam giác được xây dựng nhằm phục vụ cho việc sử dụng công cụ JUnit để tiến hành kiểm thử. Chương trình xây dựng phải đảm bảo có dữ liệu đầu vào và dữ liệu đầu ra. 
- Các hệ thống liên quan: Chương trình xây dựng trên các công cụ và môi trường sau: 
- Môi trường cài đặt ứng dụng: Microsoft Windowns 10 
- Môi trường lập trình: Netbeans IDE 8.2 
3.1.3 Các yêu cầu chức năng
- Yêu cầu kiến thức chương trình Hệ thống phải đảm bảo: 
- Tính tương thích: Thích hợp với các môi trường( môi trường cài đặt, lập trình). 
- Tính hiệu quả: Hiệu suất cao, truy xuất nhanh. 
- Các trường nhập vào kiểm tra nếu không phải là số hoặc để trống sẽ báo lỗi. 
- Giá trị nhập vào phải là số không âm.
3.2 Chương trình
Tiến hành code cho bài toán như sau:
public class Triangle {
    private int a;
    private int b;
    private int c;
    public Triangle(int a, int b, int c) throws Exception {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new Exception("dữ liệu không hợp lệ");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public int CheckTriangle() {
        boolean isTriangle = false;
        if (a < b + c && b < a + c && c < a + b) {
            isTriangle = true;
        }
        if (isTriangle) {
            if (a == b && b == c) {
                return 2;//tam giác đều
            } else if((a*a + b*b == c*c) || (a*a + c*c == b*b) || (b*b + c*c == a*a)) {
                return 3;
            }
            else if (a != b && a != c && b != c) {
                return 0;//tam giác thường
            } else {
                return 1; //tam giác cân
            }
        }
        return -1; //không phải tam giác
    }
}



Điều kiện	1	2	3	4	5	6	7	8	9	10	11
c1: a<b+c	F	T	T	T	T	T	T	T	T	T	T
c2: b<a+c	-	F	T	T	T	T	T	T	T	T	T
c3: c<a+b	-	-	F	T	T	T	T	T	T	T	T
c4: a=b	-	-	-	T	T	T	T	F	F	F	F
c5: b=c	-	-	-	T	T	F	F	T	T	F	F
c6: a=c	-	-	-	T	F	T	F	T	F	T	F
Số luật	32	16	8	1	1	1	1	1	1	1	1
a1: Không là tam giác	X	X	X								
a2: Tam giác thường											X
a3: Tam giác cân							X		X	X	
a4: Tam giác đều				X							
a5: Không hợp lệ					X	X		X			
Bảng quyết định của bài toán kiểm tra tam giác.


Từ bảng quyết định ta có thể dẫn xuất các testcase như sau:
Test Case	a	b	c	Kết quả mong đợi
1	4	1	2	Không phải là tam giác
2	1	4	2	Không phải là tam giác
3	1	2	4	Không phải là tam giác
4	5	5	5	Tam giác đều
5	?	?	?	Không khả thi
6	?	?	?	Không khả thi
7	2	2	3	Tam giác cân
8	?	?	?	Không khả thi
9	2	3	2	Tam giác cân
10	3	2	2	Tam giác cân
11	3	4	5	Tam giác thường



3.3 Hướng dẫn tạo các Unit test
Bước 1: Click phải chuột lên file Triangle.java trong package, để mở cửa sổ chức năng.




Bước 2: Click chọn Tools, sau đó chọn Create/Update.

Bước 3: Thực hiện điền Class Name, Location và Framework Junit để tiến hành tạo các unit test. Sau đó click OK, hệ thống sẽ tạo một file mới trong Test Packages cho chúng ta.

Tiến hành tạo các unit test
public class Triangle1Test {
    Triangle t;
    public Triangle1Test() {
    }
    @Test
    public void testcase1() throws Exception {
        Triangle t = new Triangle(4, 1, 2);
        int expected = -1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase2() throws Exception {
        Triangle t = new Triangle(1, 4, 2);
        int expected = -1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase3() throws Exception {
        Triangle t = new Triangle(1, 2, 4);
        int expected = -1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase4() throws Exception {
        Triangle t = new Triangle(5, 5, 5);
        int expected = 2;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase5() throws Exception {
        Triangle t = new Triangle(2, 2, 3);
        int expected = 1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }

    @Test
    public void testcase6() throws Exception {
        Triangle t = new Triangle(2, 3, 2);
        int expected = 1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase7() throws Exception {
        Triangle t = new Triangle(3, 2, 2);
        int expected = 1;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
    @Test
    public void testcase8() throws Exception {
        Triangle t = new Triangle(3, 4, 5);
        int expected = 3;
        int actual = t.CheckTriangle();
        assertEquals(expected, actual);
    }
}
Lưu ý: Kết quả của hàm được quy ước như sau:
Return -1: Không phải là tam giác.
Return 0: Là tam giác thường.
Return 1: Là tam giác cân.
Return 2: Là tam giác đều.
Kết quả của các test case



KẾT LUẬN
Trong bài báo cáo này, chúng em đã tìm hiểu về công cụ kiểm thử JUnit và sự quan trọng của việc áp dụng kiểm thử phần mềm trong quá trình phát triển phần mềm. Chúng em đã nghiên cứu cài đặt và cấu hình JUnit, viết test case, chạy và báo cáo kết quả kiểm thử, tối ưu hóa kiểm thử, và so sánh JUnit với các framework kiểm thử khác.
Qua quá trình nghiên cứu, chúng emnhận thấy JUnit là một công cụ mạnh mẽ và linh hoạt cho việc kiểm thử phần mềm trong ngôn ngữ lập trình Java. JUnit cung cấp các phương thức và annotation giúp viết và chạy test case dễ dàng và tự động. Cú pháp đơn giản và rõ ràng của JUnit cho phép người phát triển tập trung vào việc xác thực các chức năng của phần mềm một cách hiệu quả.
Ngoài ra, JUnit cũng cung cấp nhiều tính năng tiện ích như annotation @ParameterizedTest, @Timeout, @DisplayName, và khả năng tạo test suite và nhóm test case. Những tính năng này giúp tối ưu hóa quá trình kiểm thử và tăng cường khả năng tái sử dụng mã kiểm thử.
So sánh JUnit với các framework kiểm thử khác, chúng em nhận thấy JUnit có sự phổ biến rộng rãi trong cộng đồng phát triển phần mềm Java và được hỗ trợ tích cực. Điều này đảm bảo sự ổn định và liên tục cải tiến của JUnit, cùng với cộng đồng nguồn mở sẵn sàng chia sẻ kiến thức và kinh nghiệm.
Cuối cùng, việc sử dụng JUnit trong quá trình phát triển phần mềm không chỉ giúp đảm bảo chất lượng và độ tin cậy của sản phẩm mà còn tạo điều kiện thuận lợi cho việc bảo trì và mở rộng hệ thống trong tương lai. Việc áp dụng kiểm thử phần mềm thông qua JUnit sẽ giúp tối ưu hóa quy trình phát triển phần mềm và giảm thiểu các lỗi tiềm ẩn, đồng thời tăng cường sự tin tưởng của người dùng đối với phần mềm.
Tóm lại, JUnit là một công cụ kiểm thử phần mềm mạnh mẽ và hiệu quả trong ngôn ngữ lập trình Java. Việc nắm vững và áp dụng JUnit sẽ giúp các nhà phát triển nâng cao chất lượng sản phẩm và đáp ứng được yêu cầu ngày càng cao của ngành công nghiệp phần mềm.
TÀI LIỆU THAM KHẢO
