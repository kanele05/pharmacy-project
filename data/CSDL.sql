create database QLNHATHUOC
GO
use QLNHATHUOC
GO
create table ChucVu
(
	maChucVu varchar(20) primary key,
	tenChucVu nvarchar(20) not null
)

create table TaiKhoan
(
	maTaiKhoan varchar(20) primary key,
	tenDangNhap varchar(20) not null,
	matKhau varchar(150) not null,
	vaiTro nvarchar(40) not null,
	trangThai nvarchar(20) check ( trangThai in(N'Kích Hoạt',N'Khóa')) default N'Kích Hoạt'
)

create table NhanVien
(
	maNhanVien varchar(20) primary key,
	tenNhanVien nvarchar(50) not null,
	soDienThoai varchar(11) not null,
	diaChi nvarchar(50) not null,
	anhDaiDien varchar(100) not null,
	maChucVu varchar(20) not null,
	maTaiKhoan varchar(20) not null,
	foreign key (maChucVu) references ChucVu(maChucVu) on delete cascade on update cascade,
	foreign key (maTaiKhoan) references TaiKhoan(maTaiKhoan) on delete cascade on update cascade
)
create table NhaCungCap
(
	maNhaCungCap varchar(20) primary key ,
	tenNhaCungCap nvarchar(50) not null,
	soDienThoai varchar(11) not null,
	diaChi nvarchar(50) not null
)
create table PhieuNhap 
(
	maPhieuNhap varchar (20) primary key,
	ngayNhap date not null,
	trangThai nvarchar(20) not null check (trangThai in (N'Phiếu tạm', N'Đã nhập hàng', N'Đã hủy')),
	maNhaCungCap varchar(20) not null,
	maNhanVien varchar(20) not null,
	foreign key (maNhanVien) references NhanVien(maNhanVien) on delete cascade on update cascade,
	foreign key (maNhaCungCap) references NhaCungCap(maNhaCungCap) on delete cascade on update cascade
)

create table KhachHang
(
	maKhachHang varchar(20) primary key,
	soDienThoai varchar(11) not null,
	tenKhachHang nvarchar(50) not null,
	hangThanhVien varchar(20) not null,
	gioiTinh bit not null,
	thoiGianTao date not null,
	maNhanVien varchar(20) not null,
	trangThai bit default 1,
	foreign key (maNhanVien) references NhanVien(maNhanVien) on delete cascade on update cascade
)
create table PhieuDoiTra
(
	maDoiTra varchar(20) primary key,
	thoiDiem date not null,
	lyDo nvarchar(200) not null,
	loaiDoiTra nvarchar(20) check (loaiDoiTra in (N'Đổi',N'Trả')),
	thoiGianTao date not null,
	maNhanVien varchar(20),
	maKhachHang varchar(20),
	foreign key(maNhanVien) references NhanVien(maNhanVien),
	foreign key (maKhachHang) references KhachHang(maKhachHang) on delete cascade on update cascade
)
create table KhuyenMai
(
	maKhuyenMai varchar(20) primary key,
	tenChuongTrinh nvarchar(50) not null,
	tuNgay date not null,
	denNgay date not null,
	phanTramGiamGia float not null,
	dieuKienApDung int not null
)
create table Thue
(
	maThue varchar(20) primary key,
	tenThue nvarchar(50) not null,
	hieuLucTu date not null,
	hetHieuLuc date not null,
	phanTramThue float not null
)
create table KeThuoc
(
	maKe varchar(20) primary key,
	viTri nvarchar(50) not null,
	loaiKe nvarchar(50) not null,
	sucChua int not null
)

create table NhomThuoc
(
	maNhom varchar(20) primary key,
	tenNhom nvarchar(100) not null,
	maThue varchar(20) not null,
	foreign key (maThue) references Thue(maThue) on delete cascade on update cascade
)
create table Thuoc
(
	maThuoc varchar(20) primary key,
	tenThuoc nvarchar(50) not null,
	hamLuong nvarchar(20) not null,
	dangThuoc nvarchar(30) not null,
	giaThuoc float not null,
	donViTinh varchar(20) not null,
	nhaSanXuat nvarchar(50) not null,
	trangThai nvarchar(20) check (trangThai in (N'Đang Kinh Doanh',N'Ngừng Kinh Doanh')) default N'Đang Kinh Doanh',
	anhDaiDien varchar(100) not null,
	maKe varchar(20) not null,
	maNhanVien varchar(20) not null,
	maNhom varchar(20) not null,
	foreign key (maNhom) references NhomThuoc(maNhom) on delete cascade on update cascade,
	foreign key(maKe) references KeThuoc(maKe) on delete cascade on update cascade,
	foreign key(maNhanVien) references NhanVien(maNhanVien) on delete cascade on update cascade
)
create table LoThuoc
(
	maLoThuoc varchar (20) primary key,
	soLo varchar(10) not null,
	ngaySanXuat date not null,
	ngayHetHan date not null,
	giaNhap float not null,
	soLuongTon int not null,
	maThuoc varchar(20) not null,
	foreign key (maThuoc) references Thuoc(maThuoc) on delete cascade on update cascade
)
create table ChiTietPhieuNhap 
(
	soLuong int not null,
	donGiaNhap float not null,
	maPhieuNhap varchar(20),
	maLoThuoc varchar(20),
	primary key (maPhieuNhap, maLoThuoc),
	foreign key (maPhieuNhap) references PhieuNhap(maPhieuNhap),
	foreign key (maLoThuoc) references LoThuoc(maLoThuoc) on delete cascade on update cascade
)
create table ChiTietDoiTra
(
	soLuong int not null,
	maDoiTra varchar(20),
	maThuoc varchar(20),
	primary key (maDoiTra, maThuoc),
	foreign key(maDoiTra) references PhieuDoiTra(maDoiTra),
	foreign key(maThuoc) references Thuoc(maThuoc) on delete cascade on update cascade
)
create table HoaDon
(
	maHoaDon varchar(20) primary key,
	ngayLap date not null,
	phuongThucThanhToan nvarchar(20) not null,
	yeuCauKeDon bit not null,
	maKhachHang varchar(20) not null,
	maNhanVien varchar(20) not null,
	maKhuyenMai varchar(20) not null,
	foreign key (maKhuyenMai) references KhuyenMai(maKhuyenMai),
	foreign key(maKhachHang) references KhachHang(maKhachHang),
	foreign key(maNhanVien) references NhanVien(maNhanVien) on delete cascade on update cascade
)
create table ChiTietHoaDon
(
	soLuong int not null,
	maHoaDon varchar(20) not null,
	maThuoc varchar(20) not null,
	primary key (maHoaDon, maThuoc),
	foreign key(maHoaDon) references HoaDon(maHoaDon),
	foreign key(maThuoc) references Thuoc(maThuoc) on delete cascade on update cascade
)
create table PhieuDat
(
	maPhieuDat varchar(20) primary key,
	thoiGianDat date not null,
	trangThai nvarchar(20) check (trangThai in (N'Đã đặt', N'Đã nhận', N'Đã hủy')) default N'Đã đặt',
	ghiChu nvarchar(20),
	maKhachHang varchar(20) not null,
	maNhanVien varchar(20) not null,
	foreign key (maNhanVien) references NhanVien(maNhanVien),
	foreign key (maKhachHang) references KhachHang(maKhachHang) on delete cascade on update cascade
)
create table ChiTietPhieuDat
(
	soLuong int not null,
	donGiaDat float not null,
	maPhieuDat varchar(20) not null,
	maThuoc varchar(20) not null,
	primary key (maPhieuDat, maThuoc),
	foreign key (maPhieuDat) references PhieuDat(maPhieuDat),
	foreign key (maThuoc) references Thuoc(maThuoc) on delete cascade on update cascade
)
insert into ChucVu values
('CV001',N'Quản lý'),
('CV002',N'Nhân viên')
insert into TaiKhoan values
('TK001', 'admin', '123', 'admin', N'Kích Hoạt'),
('TK002', 'user1', '123', 'user', N'Kích Hoạt'),
('TK003', 'user2', '123', 'user', N'Kích Hoạt')
insert into NhanVien values
('NV001','Nguyễn Hữu Dũng','0935765186',N'Gò vấp, tphcm','asdf','CV001','TK001'),
('NV002','Nguyễn Văn Sỹ','0372145686','Q12, tphcm','asdf','CV001','TK002'),
('NV003','Nguyễn Đình Hùng','0945812321','Gò vấp, tphcm','asdf','CV001','TK003'),
('NV004', N'Phạm Minh Châu',       '0900000004', N'Q.7, TP.HCM',           'avatars/nv004.jpg', 'CV002', 'TK001'),
('NV005', N'Đỗ Thị Hạnh',          '0900000005', N'TP. Thủ Đức, TP.HCM',   'avatars/nv005.jpg', 'CV001', 'TK002'),
('NV006', N'Bùi Quang Huy',        '0900000006', N'Q.10, TP.HCM',          'avatars/nv006.jpg', 'CV002', 'TK003'),
('NV007', N'Võ Thị Lan',           '0900000007', N'Q.11, TP.HCM',          'avatars/nv007.jpg', 'CV001', 'TK001'),
('NV008', N'Ngô Đức Thắng',        '0900000008', N'Q. Phú Nhuận, TP.HCM',  'avatars/nv008.jpg', 'CV002', 'TK002'),
('NV009', N'Đặng Thị Yến',         '0900000009', N'Q. Tân Bình, TP.HCM',   'avatars/nv009.jpg', 'CV001', 'TK003'),
('NV010', N'Phan Thanh Tùng',      '0900000010', N'Q. Tân Phú, TP.HCM',    'avatars/nv010.jpg', 'CV002', 'TK001'),

('NV011', N'Nguyễn Thị Mai',       '0900000011', N'Q. Gò Vấp, TP.HCM',     'avatars/nv011.jpg', 'CV001', 'TK002'),
('NV012', N'Trần Quốc Bảo',        '0900000012', N'Q. Bình Thạnh, TP.HCM', 'avatars/nv012.jpg', 'CV002', 'TK003'),
('NV013', N'Lê Thùy Dung',         '0900000013', N'Q.1, TP.HCM',           'avatars/nv013.jpg', 'CV001', 'TK001'),
('NV014', N'Phạm Gia Huy',         '0900000014', N'Q.3, TP.HCM',           'avatars/nv014.jpg', 'CV002', 'TK002'),
('NV015', N'Đỗ Ngọc Ánh',          '0900000015', N'Q.5, TP.HCM',           'avatars/nv015.jpg', 'CV001', 'TK003'),
('NV016', N'Bùi Thị Thu',          '0900000016', N'Q.7, TP.HCM',           'avatars/nv016.jpg', 'CV002', 'TK001'),
('NV017', N'Võ Minh Khoa',         '0900000017', N'TP. Thủ Đức, TP.HCM',   'avatars/nv017.jpg', 'CV001', 'TK002'),
('NV018', N'Ngô Thị Vân',          '0900000018', N'Q.10, TP.HCM',          'avatars/nv018.jpg', 'CV002', 'TK003'),
('NV019', N'Đặng Quốc Khánh',      '0900000019', N'Q.11, TP.HCM',          'avatars/nv019.jpg', 'CV001', 'TK001'),
('NV020', N'Phan Thị Hương',       '0900000020', N'Q. Phú Nhuận, TP.HCM',  'avatars/nv020.jpg', 'CV002', 'TK002'),

('NV021', N'Nguyễn Nhật Linh',     '0900000021', N'Q. Tân Bình, TP.HCM',   'avatars/nv021.jpg', 'CV001', 'TK003'),
('NV022', N'Trần Văn Tú',          '0900000022', N'Q. Tân Phú, TP.HCM',    'avatars/nv022.jpg', 'CV002', 'TK001'),
('NV023', N'Lê Thị Kim Oanh',      '0900000023', N'Q. Gò Vấp, TP.HCM',     'avatars/nv023.jpg', 'CV001', 'TK002'),
('NV024', N'Phạm Đức Long',        '0900000024', N'Q. Bình Thạnh, TP.HCM', 'avatars/nv024.jpg', 'CV002', 'TK003'),
('NV025', N'Đỗ Thị Mỹ Lệ',         '0900000025', N'Q.1, TP.HCM',           'avatars/nv025.jpg', 'CV001', 'TK001'),
('NV026', N'Bùi Anh Tuấn',         '0900000026', N'Q.3, TP.HCM',           'avatars/nv026.jpg', 'CV002', 'TK002'),
('NV027', N'Võ Thị Trà My',        '0900000027', N'Q.5, TP.HCM',           'avatars/nv027.jpg', 'CV001', 'TK003'),
('NV028', N'Ngô Minh Chí',         '0900000028', N'Q.7, TP.HCM',           'avatars/nv028.jpg', 'CV002', 'TK001'),
('NV029', N'Đặng Thị Quỳnh',       '0900000029', N'TP. Thủ Đức, TP.HCM',   'avatars/nv029.jpg', 'CV001', 'TK002'),
('NV030', N'Phan Văn Lực',         '0900000030', N'Q.10, TP.HCM',          'avatars/nv030.jpg', 'CV002', 'TK003'),

('NV031', N'Nguyễn Thị Thanh',     '0900000031', N'Q.11, TP.HCM',          'avatars/nv031.jpg', 'CV001', 'TK001'),
('NV032', N'Trần Hải Nam',         '0900000032', N'Q. Phú Nhuận, TP.HCM',  'avatars/nv032.jpg', 'CV002', 'TK002'),
('NV033', N'Lê Bảo Châu',          '0900000033', N'Q. Tân Bình, TP.HCM',   'avatars/nv033.jpg', 'CV001', 'TK003'),
('NV034', N'Phạm Thị Ly',          '0900000034', N'Q. Tân Phú, TP.HCM',    'avatars/nv034.jpg', 'CV002', 'TK001'),
('NV035', N'Đỗ Thanh Phong',       '0900000035', N'Q. Gò Vấp, TP.HCM',     'avatars/nv035.jpg', 'CV001', 'TK002'),
('NV036', N'Bùi Thị Như',          '0900000036', N'Q. Bình Thạnh, TP.HCM', 'avatars/nv036.jpg', 'CV002', 'TK003'),
('NV037', N'Võ Nhật Tân',          '0900000037', N'Q.1, TP.HCM',           'avatars/nv037.jpg', 'CV001', 'TK001'),
('NV038', N'Ngô Thu Trang',        '0900000038', N'Q.3, TP.HCM',           'avatars/nv038.jpg', 'CV002', 'TK002'),
('NV039', N'Đặng Minh Đức',        '0900000039', N'Q.5, TP.HCM',           'avatars/nv039.jpg', 'CV001', 'TK003'),
('NV040', N'Phan Thị Yến',         '0900000040', N'Q.7, TP.HCM',           'avatars/nv040.jpg', 'CV002', 'TK001'),

('NV041', N'Nguyễn Hữu Tài',       '0900000041', N'TP. Thủ Đức, TP.HCM',   'avatars/nv041.jpg', 'CV001', 'TK002'),
('NV042', N'Trần Lệ Quyên',        '0900000042', N'Q.10, TP.HCM',          'avatars/nv042.jpg', 'CV002', 'TK003'),
('NV043', N'Lê Văn Phúc',          '0900000043', N'Q.11, TP.HCM',          'avatars/nv043.jpg', 'CV001', 'TK001'),
('NV044', N'Phạm Thu Thảo',        '0900000044', N'Q. Phú Nhuận, TP.HCM',  'avatars/nv044.jpg', 'CV002', 'TK002'),
('NV045', N'Đỗ Hải Yến',           '0900000045', N'Q. Tân Bình, TP.HCM',   'avatars/nv045.jpg', 'CV001', 'TK003'),
('NV046', N'Bùi Hồng Sơn',         '0900000046', N'Q. Tân Phú, TP.HCM',    'avatars/nv046.jpg', 'CV002', 'TK001'),
('NV047', N'Võ Kim Ngân',          '0900000047', N'Q. Gò Vấp, TP.HCM',     'avatars/nv047.jpg', 'CV001', 'TK002'),
('NV048', N'Ngô Trúc Linh',        '0900000048', N'Q. Bình Thạnh, TP.HCM', 'avatars/nv048.jpg', 'CV002', 'TK003'),
('NV049', N'Đặng Anh Khoa',        '0900000049', N'Q.1, TP.HCM',           'avatars/nv049.jpg', 'CV001', 'TK001'),
('NV050', N'Phan Thảo Nhi',        '0900000050', N'Q.3, TP.HCM',           'avatars/nv050.jpg', 'CV002', 'TK002');
INSERT INTO KhachHang (maKhachHang, soDienThoai, tenKhachHang, hangThanhVien, gioiTinh, thoiGianTao, maNhanVien) VALUES
('KH001', '0900000001', N'Nguyễn Văn A',       N'Thường',   1, '2024-01-05', 'NV001'),
('KH002', '0900000002', N'Trần Thị B',         N'Bạc',      0, '2024-01-06', 'NV002'),
('KH003', '0900000003', N'Lê Văn C',           N'Vàng',     1, '2024-01-07', 'NV003'),
('KH004', '0900000004', N'Phạm Thị D',         N'Kim Cương',0, '2024-01-08', 'NV004'),
('KH005', '0900000005', N'Võ Văn E',           N'Thường',   1, '2024-02-01', 'NV005'),
('KH006', '0900000006', N'Đỗ Thị F',           N'Bạc',      0, '2024-02-02', 'NV006'),
('KH007', '0900000007', N'Bùi Văn G',          N'Vàng',     1, '2024-02-03', 'NV007'),
('KH008', '0900000008', N'Đặng Thị H',         N'Kim Cương',0, '2024-02-04', 'NV008'),
('KH009', '0900000009', N'Ngô Văn I',          N'Thường',   1, '2024-03-10', 'NV009'),
('KH010', '0900000010', N'Phan Thị K',         N'Bạc',      0, '2024-03-11', 'NV010'),

('KH011', '0900000011', N'Nguyễn Thị L',       N'Vàng',     0, '2024-03-12', 'NV011'),
('KH012', '0900000012', N'Trần Văn M',         N'Kim Cương',1, '2024-03-13', 'NV012'),
('KH013', '0900000013', N'Lê Thị N',           N'Thường',   0, '2024-04-01', 'NV013'),
('KH014', '0900000014', N'Phạm Văn O',         N'Bạc',      1, '2024-04-02', 'NV014'),
('KH015', '0900000015', N'Võ Thị P',           N'Vàng',     0, '2024-04-03', 'NV015'),
('KH016', '0900000016', N'Đỗ Văn Q',           N'Kim Cương',1, '2024-04-04', 'NV016'),
('KH017', '0900000017', N'Bùi Thị R',          N'Thường',   0, '2024-05-06', 'NV017'),
('KH018', '0900000018', N'Đặng Văn S',         N'Bạc',      1, '2024-05-07', 'NV018'),
('KH019', '0900000019', N'Ngô Thị T',          N'Vàng',     0, '2024-05-08', 'NV019'),
('KH020', '0900000020', N'Phan Văn U',         N'Kim Cương',1, '2024-05-09', 'NV020'),

('KH021', '0900000021', N'Nguyễn Thị V',       N'Thường',   0, '2024-06-15', 'NV021'),
('KH022', '0900000022', N'Trần Văn X',         N'Bạc',      1, '2024-06-16', 'NV022'),
('KH023', '0900000023', N'Lê Thị Y',           N'Vàng',     0, '2024-06-17', 'NV023'),
('KH024', '0900000024', N'Phạm Văn Z',         N'Kim Cương',1, '2024-06-18', 'NV024'),
('KH025', '0900000025', N'Võ Minh An',         N'Thường',   1, '2024-07-01', 'NV025'),
('KH026', '0900000026', N'Đỗ Mỹ Bình',         N'Bạc',      0, '2024-07-02', 'NV026'),
('KH027', '0900000027', N'Bùi Hoàng C',        N'Vàng',     1, '2024-07-03', 'NV027'),
('KH028', '0900000028', N'Đặng Thu D',         N'Kim Cương',0, '2024-07-04', 'NV028'),
('KH029', '0900000029', N'Ngô Nhật E',         N'Thường',   1, '2024-08-05', 'NV029'),
('KH030', '0900000030', N'Phan Hải F',         N'Bạc',      0, '2024-08-06', 'NV030'),

('KH031', '0900000031', N'Nguyễn Ngọc G',      N'Vàng',     1, '2024-08-07', 'NV031'),
('KH032', '0900000032', N'Trần Khánh H',       N'Kim Cương',0, '2024-08-08', 'NV032'),
('KH033', '0900000033', N'Lê Trà I',           N'Thường',   1, '2024-09-10', 'NV033'),
('KH034', '0900000034', N'Phạm Thu J',         N'Bạc',      0, '2024-09-11', 'NV034'),
('KH035', '0900000035', N'Võ Quốc K',          N'Vàng',     1, '2024-09-12', 'NV035'),
('KH036', '0900000036', N'Đỗ Kim L',           N'Kim Cương',0, '2024-09-13', 'NV036'),
('KH037', '0900000037', N'Bùi Tuấn M',         N'Thường',   1, '2024-10-01', 'NV037'),
('KH038', '0900000038', N'Đặng Quỳnh N',       N'Bạc',      0, '2024-10-02', 'NV038'),
('KH039', '0900000039', N'Ngô Thế O',          N'Vàng',     1, '2024-10-03', 'NV039'),
('KH040', '0900000040', N'Phan Hữu P',         N'Kim Cương',0, '2024-10-04', 'NV040'),

('KH041', '0900000041', N'Nguyễn Gia Q',       N'Thường',   1, '2024-11-06', 'NV041'),
('KH042', '0900000042', N'Trần Ý R',           N'Bạc',      0, '2024-11-07', 'NV042'),
('KH043', '0900000043', N'Lê Bảo S',           N'Vàng',     1, '2024-11-08', 'NV043'),
('KH044', '0900000044', N'Phạm Nhật T',        N'Kim Cương',0, '2024-11-09', 'NV044'),
('KH045', '0900000045', N'Võ Trúc U',          N'Thường',   1, '2024-12-01', 'NV045'),
('KH046', '0900000046', N'Đỗ Thiên V',         N'Bạc',      0, '2024-12-02', 'NV046'),
('KH047', '0900000047', N'Bùi Thanh W',        N'Vàng',     1, '2024-12-03', 'NV047'),
('KH048', '0900000048', N'Đặng Mai X',         N'Kim Cương',0, '2024-12-04', 'NV048'),
('KH049', '0900000049', N'Ngô Đức Y',          N'Thường',   1, '2025-01-05', 'NV049'),
('KH050', '0900000050', N'Phan Hạ Z',          N'Bạc',      0, '2025-01-06', 'NV050');
INSERT INTO KeThuoc (maKe, viTri, loaiKe, sucChua) VALUES
('KE001', N'Khu A - Hàng 1',        N'Kệ OTC',                    120),
('KE002', N'Khu A - Hàng 2',        N'Kệ kê đơn',                 100),
('KE003', N'Khu A - Hàng 3',        N'Kệ vitamin - khoáng',       140),
('KE004', N'Khu B - Hàng 1',        N'Kệ tiêu hóa',               110),
('KE005', N'Khu B - Hàng 2',        N'Kệ hô hấp',                 110),
('KE006', N'Khu B - Hàng 3',        N'Kệ da liễu',                90),
('KE007', N'Khu C - Gần quầy thu ngân', N'Kệ thực phẩm chức năng', 160),
('KE008', N'Khu C - Gần cửa ra vào',   N'Kệ khuyến mãi',           180),
('KE009', N'Kho lạnh - Ngăn 1',     N'Kệ lạnh',                   60),
('KE010', N'Kho lạnh - Ngăn 2',     N'Kệ lạnh',                   60),
('KE011', N'Quầy dược sĩ - Sau quầy', N'Tủ thuốc kê đơn',         80),
('KE012', N'Kho tổng - Kệ pallet 1',  N'Kệ lưu trữ dự phòng',     300);
INSERT INTO Thue (maThue, tenThue, hieuLucTu, hetHieuLuc, phanTramThue) VALUES
('TH001', N'Thuế VAT 5%',        '2022-01-01', '2023-12-31', 0.05),
('TH002', N'Thuế VAT 8%',        '2024-01-01', '2024-12-31', 0.08),
('TH003', N'Thuế VAT 10%',       '2025-01-01', '2029-12-31', 0.10),
('TH004', N'Thuế Tiêu thụ đặc biệt 20%', '2023-01-01', '2027-12-31', 0.20),
('TH005', N'Thuế Bảo vệ môi trường 3%',  '2022-06-01', '2026-05-31', 0.03),
('TH006', N'Thuế Nhập khẩu ưu đãi 2%',   '2023-04-01', '2026-03-31', 0.02);
INSERT INTO NhomThuoc (maNhom, tenNhom, maThue) VALUES
('NH001', N'Giảm đau - Hạ sốt',                 'TH003'), 
('NH002', N'Kháng sinh',                        'TH003'),
('NH003', N'Kháng viêm - Corticoid',            'TH003'),
('NH004', N'Vitamin & Khoáng chất',             'TH001'), 
('NH005', N'Hô hấp (ho - cảm cúm - dị ứng)',    'TH003'),
('NH006', N'Tiêu hóa - Gan mật',                'TH003'),
('NH007', N'Tim mạch - Huyết áp',               'TH003'),
('NH008', N'Đái tháo đường - Nội tiết',         'TH003'),
('NH009', N'Da liễu',                            'TH003'),
('NH010', N'Tai - Mũi - Họng',                  'TH003'),
('NH011', N'Nhãn khoa (Mắt)',                   'TH001');

INSERT INTO Thuoc (maThuoc, tenThuoc, hamLuong, dangThuoc, giaThuoc, donViTinh, nhaSanXuat, trangThai, anhDaiDien, maKe, maNhanVien, maNhom) VALUES
('T001',  N'Paracetamol',              N'500mg',  N'Viên nén',      1800.0,  'viên',  N'Dược ABC',         N'Đang Kinh Doanh',  'imgs/t001.jpg',  'KE001', 'NV001', 'NH001'),
('T002',  N'Amoxicillin',              N'500mg',  N'Viên nang',     3500.0,  'viên',  N'Dược XYZ',         N'Đang Kinh Doanh',  'imgs/t002.jpg',  'KE002', 'NV002', 'NH002'),
('T003',  N'Azithromycin',             N'250mg',  N'Viên nén',      5200.0,  'viên',  N'Dược Minh An',     N'Ngừng Kinh Doanh', 'imgs/t003.jpg',  'KE003', 'NV003', 'NH002'),
('T004',  N'Ibuprofen',                N'400mg',  N'Viên nén',      2400.0,  'viên',  N'Dược Hòa Phát',    N'Đang Kinh Doanh',  'imgs/t004.jpg',  'KE004', 'NV004', 'NH001'),
('T005',  N'Naproxen',                 N'250mg',  N'Viên nén',      3100.0,  'viên',  N'Dược Việt Đức',    N'Đang Kinh Doanh',  'imgs/t005.jpg',  'KE005', 'NV005', 'NH001'),
('T006',  N'Acetylcystein',            N'200mg',  N'Gói bột',       4500.0,  'gói',   N'Dược An Khang',     N'Ngừng Kinh Doanh', 'imgs/t006.jpg',  'KE006', 'NV006', 'NH005'),
('T007',  N'Loratadine',               N'10mg',   N'Viên nén',      2300.0,  'viên',  N'Dược Tâm An',       N'Đang Kinh Doanh',  'imgs/t007.jpg',  'KE007', 'NV007', 'NH005'),
('T008',  N'Cetirizine',               N'10mg',   N'Viên nén',      2100.0,  'viên',  N'Dược Đại Nam',      N'Đang Kinh Doanh',  'imgs/t008.jpg',  'KE008', 'NV008', 'NH005'),
('T009',  N'Prednisolone',             N'5mg',    N'Viên nén',      1900.0,  'viên',  N'Dược Trường An',    N'Ngừng Kinh Doanh', 'imgs/t009.jpg',  'KE009', 'NV009', 'NH003'),
('T010',  N'Dexamethasone',            N'0.5mg',  N'Viên nén',      1700.0,  'viên',  N'Dược Thiên Long',   N'Đang Kinh Doanh',  'imgs/t010.jpg',  'KE010', 'NV010', 'NH003'),

('T011',  N'Omeprazole',               N'20mg',   N'Viên nang',     3800.0,  'viên',  N'Dược Sông Hồng',    N'Đang Kinh Doanh',  'imgs/t011.jpg',  'KE011', 'NV011', 'NH006'),
('T012',  N'Pantoprazole',             N'40mg',   N'Viên nén',      5200.0,  'viên',  N'Dược Sao Mai',      N'Ngừng Kinh Doanh', 'imgs/t012.jpg',  'KE012', 'NV012', 'NH006'),
('T013',  N'Metformin',                N'500mg',  N'Viên nén',      2600.0,  'viên',  N'Dược Bình Minh',    N'Đang Kinh Doanh',  'imgs/t013.jpg',  'KE001', 'NV013', 'NH008'),
('T014',  N'Gliclazide',               N'80mg',   N'Viên nén',      3400.0,  'viên',  N'Dược Ánh Dương',    N'Đang Kinh Doanh',  'imgs/t014.jpg',  'KE002', 'NV014', 'NH008'),
('T015',  N'Amlodipine',               N'5mg',    N'Viên nén',      2900.0,  'viên',  N'Dược Phú Thịnh',    N'Ngừng Kinh Doanh', 'imgs/t015.jpg',  'KE003', 'NV015', 'NH007'),
('T016',  N'Losartan',                 N'50mg',   N'Viên nén',      4100.0,  'viên',  N'Dược Hưng Thịnh',   N'Đang Kinh Doanh',  'imgs/t016.jpg',  'KE004', 'NV016', 'NH007'),
('T017',  N'Perindopril',              N'5mg',    N'Viên nén',      4300.0,  'viên',  N'Dược Mekong',       N'Đang Kinh Doanh',  'imgs/t017.jpg',  'KE005', 'NV017', 'NH007'),
('T018',  N'Atorvastatin',             N'20mg',   N'Viên nén',      6200.0,  'viên',  N'Dược Tân Bình',     N'Ngừng Kinh Doanh', 'imgs/t018.jpg',  'KE006', 'NV018', 'NH007'),
('T019',  N'Simvastatin',              N'10mg',   N'Viên nén',      3000.0,  'viên',  N'Dược Kim Long',     N'Đang Kinh Doanh',  'imgs/t019.jpg',  'KE007', 'NV019', 'NH007'),
('T020',  N'Clopidogrel',              N'75mg',   N'Viên nén',      8500.0,  'viên',  N'Dược Việt Nhật',    N'Đang Kinh Doanh',  'imgs/t020.jpg',  'KE008', 'NV020', 'NH007'),

('T021',  N'Insulin R',                N'100IU',  N'Lọ tiêm',       95000.0, 'lọ',    N'BioPharma VN',      N'Ngừng Kinh Doanh', 'imgs/t021.jpg',  'KE009', 'NV021', 'NH008'),
('T022',  N'Insulin NPH',              N'100IU',  N'Lọ tiêm',       98000.0, 'lọ',    N'BioPharma VN',      N'Đang Kinh Doanh',  'imgs/t022.jpg',  'KE010', 'NV022', 'NH008'),
('T023',  N'Salbutamol',               N'100mcg', N'Bình xịt',      48000.0, 'bình',  N'Dược Hồng Hà',      N'Đang Kinh Doanh',  'imgs/t023.jpg',  'KE011', 'NV023', 'NH005'),
('T024',  N'Budesonide',               N'200mcg', N'Bình xịt',      51000.0, 'bình',  N'Dược Hồng Hà',      N'Ngừng Kinh Doanh', 'imgs/t024.jpg',  'KE012', 'NV024', 'NH005'),
('T025',  N'Vitamin C',                N'1000mg', N'Viên sủi',      5500.0,  'viên',  N'Dược Shine',        N'Đang Kinh Doanh',  'imgs/t025.jpg',  'KE001', 'NV025', 'NH004'),
('T026',  N'Vitamin B1',               N'10mg',   N'Viên nén',      900.0,   'viên',  N'Dược Shine',        N'Đang Kinh Doanh',  'imgs/t026.jpg',  'KE002', 'NV026', 'NH004'),
('T027',  N'Magnesium B6',             N'...',    N'Viên nén',      4200.0,  'viên',  N'Dược NewLife',      N'Ngừng Kinh Doanh', 'imgs/t027.jpg',  'KE003', 'NV027', 'NH004'),
('T028',  N'Kali clorid',              N'600mg',  N'Viên nén',      3100.0,  'viên',  N'Dược NewLife',      N'Đang Kinh Doanh',  'imgs/t028.jpg',  'KE004', 'NV028', 'NH004'),
('T029',  N'Domperidone',              N'10mg',   N'Viên nén',      2700.0,  'viên',  N'Dược Đại Tín',      N'Đang Kinh Doanh',  'imgs/t029.jpg',  'KE005', 'NV029', 'NH006'),
('T030',  N'Esomeprazole',             N'20mg',   N'Viên nén',      5700.0,  'viên',  N'Dược Đại Tín',      N'Ngừng Kinh Doanh', 'imgs/t030.jpg',  'KE006', 'NV030', 'NH006'),

('T031',  N'Metronidazole',            N'250mg',  N'Viên nén',      1600.0,  'viên',  N'Dược Phú Mỹ',       N'Đang Kinh Doanh',  'imgs/t031.jpg',  'KE007', 'NV031', 'NH002'),
('T032',  N'Levofloxacin',             N'500mg',  N'Viên nén',      9200.0,  'viên',  N'Dược Phú Mỹ',       N'Đang Kinh Doanh',  'imgs/t032.jpg',  'KE008', 'NV032', 'NH002'),
('T033',  N'Ciprofloxacin',            N'500mg',  N'Viên nén',      7100.0,  'viên',  N'Dược Phú Mỹ',       N'Ngừng Kinh Doanh', 'imgs/t033.jpg',  'KE009', 'NV033', 'NH002'),
('T034',  N'Aciclovir',                N'400mg',  N'Viên nén',      3600.0,  'viên',  N'Dược Việt Á',       N'Đang Kinh Doanh',  'imgs/t034.jpg',  'KE010', 'NV034', 'NH002'),
('T035',  N'Oseltamivir',              N'75mg',   N'Viên nang',     18500.0, 'viên',  N'Dược Việt Á',       N'Đang Kinh Doanh',  'imgs/t035.jpg',  'KE011', 'NV035', 'NH005'),
('T036',  N'Acetazolamide',            N'250mg',  N'Viên nén',      5400.0,  'viên',  N'Dược Thiên Ý',      N'Ngừng Kinh Doanh', 'imgs/t036.jpg',  'KE012', 'NV036', 'NH001'),
('T037',  N'Montelukast',              N'10mg',   N'Viên nén',      7600.0,  'viên',  N'Dược Song Hỷ',      N'Đang Kinh Doanh',  'imgs/t037.jpg',  'KE001', 'NV037', 'NH005'),
('T038',  N'Fexofenadine',             N'120mg',  N'Viên nén',      6900.0,  'viên',  N'Dược Song Hỷ',      N'Đang Kinh Doanh',  'imgs/t038.jpg',  'KE002', 'NV038', 'NH005'),
('T039',  N'Diclofenac',               N'50mg',   N'Viên nén',      2100.0,  'viên',  N'Dược Trường Sơn',   N'Ngừng Kinh Doanh', 'imgs/t039.jpg',  'KE003', 'NV039', 'NH001'),
('T040',  N'Celecoxib',                N'200mg',  N'Viên nang',     8400.0,  'viên',  N'Dược Trường Sơn',   N'Đang Kinh Doanh',  'imgs/t040.jpg',  'KE004', 'NV040', 'NH001'),

('T041',  N'Guaifenesin',              N'100mg',  N'Siro',          32000.0, 'chai',  N'Dược Sao Bắc',      N'Đang Kinh Doanh',  'imgs/t041.jpg',  'KE005', 'NV041', 'NH005'),
('T042',  N'Dextromethorphan',         N'15mg/5ml',N'Siro',         30000.0, 'chai',  N'Dược Sao Bắc',      N'Ngừng Kinh Doanh', 'imgs/t042.jpg',  'KE006', 'NV042', 'NH005'),
('T043',  N'Acid folic',               N'5mg',    N'Viên nén',      1200.0,  'viên',  N'Dược Tân Phú',      N'Đang Kinh Doanh',  'imgs/t043.jpg',  'KE007', 'NV043', 'NH004'),
('T044',  N'Calcium-D3',               N'500/200',N'Viên nhai',     4500.0,  'viên',  N'Dược Tân Phú',      N'Đang Kinh Doanh',  'imgs/t044.jpg',  'KE008', 'NV044', 'NH004'),
('T045',  N'Loperamide',               N'2mg',    N'Viên nén',      1900.0,  'viên',  N'Dược Thiện Tâm',    N'Ngừng Kinh Doanh', 'imgs/t045.jpg',  'KE009', 'NV045', 'NH006'),
('T046',  N'Rifaximin',                N'200mg',  N'Viên nén',      12500.0, 'viên',  N'Dược Thiện Tâm',    N'Đang Kinh Doanh',  'imgs/t046.jpg',  'KE010', 'NV046', 'NH006'),
('T047',  N'Rabeprazole',              N'20mg',   N'Viên nén',      6000.0,  'viên',  N'Dược Thiện Tâm',    N'Đang Kinh Doanh',  'imgs/t047.jpg',  'KE011', 'NV047', 'NH006'),
('T048',  N'Bisacodyl',                N'5mg',    N'Viên bao',      2300.0,  'viên',  N'Dược Thiện Tâm',    N'Ngừng Kinh Doanh', 'imgs/t048.jpg',  'KE012', 'NV048', 'NH006'),
('T049',  N'Clotrimazole',             N'1%',     N'Kem bôi',       18000.0, 'tuýp',  N'Dược Á Châu',       N'Đang Kinh Doanh',  'imgs/t049.jpg',  'KE001', 'NV049', 'NH009'),
('T050',  N'Ketoconazole',             N'2%',     N'Kem bôi',       22000.0, 'tuýp',  N'Dược Á Châu',       N'Đang Kinh Doanh',  'imgs/t050.jpg',  'KE002', 'NV050', 'NH009'),

('T051',  N'Miconazole',               N'2%',     N'Dung dịch',     26000.0, 'chai',  N'Dược Á Châu',       N'Ngừng Kinh Doanh', 'imgs/t051.jpg',  'KE003', 'NV001', 'NH009'),
('T052',  N'Fusidic acid',             N'2%',     N'Kem bôi',       34000.0, 'tuýp',  N'Dược Á Châu',       N'Đang Kinh Doanh',  'imgs/t052.jpg',  'KE004', 'NV002', 'NH009'),
('T053',  N'Betamethasone',            N'0.1%',   N'Kem bôi',       29000.0, 'tuýp',  N'Dược Thành Công',    N'Đang Kinh Doanh',  'imgs/t053.jpg',  'KE005', 'NV003', 'NH003'),
('T054',  N'Adapalene',                N'0.1%',   N'Gel bôi',       37000.0, 'tuýp',  N'Dược Thành Công',    N'Ngừng Kinh Doanh', 'imgs/t054.jpg',  'KE006', 'NV004', 'NH009'),
('T055',  N'Erythromycin',             N'500mg',  N'Viên nén',      4100.0,  'viên',  N'Dược Thành Công',    N'Đang Kinh Doanh',  'imgs/t055.jpg',  'KE007', 'NV005', 'NH002'),
('T056',  N'Roxithromycin',            N'150mg',  N'Viên nén',      5200.0,  'viên',  N'Dược An Thịnh',      N'Đang Kinh Doanh',  'imgs/t056.jpg',  'KE008', 'NV006', 'NH002'),
('T057',  N'Linezolid',                N'600mg',  N'Viên nén',      32000.0, 'viên',  N'Dược An Thịnh',      N'Ngừng Kinh Doanh', 'imgs/t057.jpg',  'KE009', 'NV007', 'NH002'),
('T058',  N'Fluconazole',              N'150mg',  N'Viên nang',     7400.0,  'viên',  N'Dược An Thịnh',      N'Đang Kinh Doanh',  'imgs/t058.jpg',  'KE010', 'NV008', 'NH002'),
('T059',  N'Valacyclovir',             N'500mg',  N'Viên nén',      21000.0, 'viên',  N'Dược Hòa Bình',      N'Đang Kinh Doanh',  'imgs/t059.jpg',  'KE011', 'NV009', 'NH002'),
('T060',  N'Famciclovir',              N'250mg',  N'Viên nén',      19500.0, 'viên',  N'Dược Hòa Bình',      N'Ngừng Kinh Doanh', 'imgs/t060.jpg',  'KE012', 'NV010', 'NH002'),

('T061',  N'Chlorpheniramine',         N'4mg',    N'Viên nén',      800.0,   'viên',  N'Dược Hoàng Gia',     N'Đang Kinh Doanh',  'imgs/t061.jpg',  'KE001', 'NV011', 'NH005'),
('T062',  N'Fentanyl patch',           N'25mcg/h',N'Miếng dán',     120000.0,'miếng', N'Dược Hoàng Gia',     N'Đang Kinh Doanh',  'imgs/t062.jpg',  'KE002', 'NV012', 'NH001'),
('T063',  N'Codein phosphate',         N'10mg',   N'Viên nén',      3500.0,  'viên',  N'Dược Hoàng Gia',     N'Ngừng Kinh Doanh', 'imgs/t063.jpg',  'KE003', 'NV013', 'NH001'),
('T064',  N'Meloxicam',                N'7.5mg',  N'Viên nén',      3000.0,  'viên',  N'Dược Bình An',       N'Đang Kinh Doanh',  'imgs/t064.jpg',  'KE004', 'NV014', 'NH001'),
('T065',  N'Allopurinol',              N'300mg',  N'Viên nén',      2800.0,  'viên',  N'Dược Bình An',       N'Đang Kinh Doanh',  'imgs/t065.jpg',  'KE005', 'NV015', 'NH001'),
('T066',  N'Colchicine',               N'1mg',    N'Viên nén',      4200.0,  'viên',  N'Dược Bình An',       N'Ngừng Kinh Doanh', 'imgs/t066.jpg',  'KE006', 'NV016', 'NH001'),
('T067',  N'Bisoprolol',               N'5mg',    N'Viên nén',      3900.0,  'viên',  N'Dược Hải Hà',        N'Đang Kinh Doanh',  'imgs/t067.jpg',  'KE007', 'NV017', 'NH007'),
('T068',  N'Metoprolol',               N'50mg',   N'Viên nén',      3600.0,  'viên',  N'Dược Hải Hà',        N'Đang Kinh Doanh',  'imgs/t068.jpg',  'KE008', 'NV018', 'NH007'),
('T069',  N'Enalapril',                N'10mg',   N'Viên nén',      3100.0,  'viên',  N'Dược Hải Hà',        N'Ngừng Kinh Doanh', 'imgs/t069.jpg',  'KE009', 'NV019', 'NH007'),
('T070',  N'Indapamide',               N'1.5mg',  N'Viên nén',      3300.0,  'viên',  N'Dược Hải Hà',        N'Đang Kinh Doanh',  'imgs/t070.jpg',  'KE010', 'NV020', 'NH007'),

('T071',  N'Furosemide',               N'40mg',   N'Viên nén',      2000.0,  'viên',  N'Dược Long Châu',     N'Đang Kinh Doanh',  'imgs/t071.jpg',  'KE011', 'NV021', 'NH007'),
('T072',  N'Spironolactone',           N'25mg',   N'Viên nén',      3500.0,  'viên',  N'Dược Long Châu',     N'Ngừng Kinh Doanh', 'imgs/t072.jpg',  'KE012', 'NV022', 'NH007'),
('T073',  N'Glyceryl trinitrate',      N'0.5mg',  N'Viên ngậm',     4500.0,  'viên',  N'Dược Long Châu',     N'Đang Kinh Doanh',  'imgs/t073.jpg',  'KE001', 'NV023', 'NH007'),
('T074',  N'Ranitidine',               N'150mg',  N'Viên nén',      2500.0,  'viên',  N'Dược Long Châu',     N'Đang Kinh Doanh',  'imgs/t074.jpg',  'KE002', 'NV024', 'NH006'),
('T075',  N'Sucralfate',               N'1g',     N'Gói bột',       3200.0,  'gói',   N'Dược Thanh Bình',    N'Ngừng Kinh Doanh', 'imgs/t075.jpg',  'KE003', 'NV025', 'NH006'),
('T076',  N'Hyoscine butylbromide',    N'10mg',   N'Viên nén',      2900.0,  'viên',  N'Dược Thanh Bình',    N'Đang Kinh Doanh',  'imgs/t076.jpg',  'KE004', 'NV026', 'NH006'),
('T077',  N'Pioglitazone',             N'15mg',   N'Viên nén',      6600.0,  'viên',  N'Dược Thanh Bình',    N'Đang Kinh Doanh',  'imgs/t077.jpg',  'KE005', 'NV027', 'NH008'),
('T078',  N'Sitagliptin',              N'100mg',  N'Viên nén',      18500.0, 'viên',  N'Dược Thanh Bình',    N'Ngừng Kinh Doanh', 'imgs/t078.jpg',  'KE006', 'NV028', 'NH008'),
('T079',  N'Empagliflozin',            N'10mg',   N'Viên nén',      21000.0, 'viên',  N'Dược Thanh Bình',    N'Đang Kinh Doanh',  'imgs/t079.jpg',  'KE007', 'NV029', 'NH008'),
('T080',  N'Vildagliptin',             N'50mg',   N'Viên nén',      14000.0, 'viên',  N'Dược Thanh Bình',    N'Đang Kinh Doanh',  'imgs/t080.jpg',  'KE008', 'NV030', 'NH008'),

('T081',  N'Levothyroxine',            N'50mcg',  N'Viên nén',      2700.0,  'viên',  N'Dược Phương Nam',    N'Ngừng Kinh Doanh', 'imgs/t081.jpg',  'KE009', 'NV031', 'NH008'),
('T082',  N'Methimazole',              N'5mg',    N'Viên nén',      3600.0,  'viên',  N'Dược Phương Nam',    N'Đang Kinh Doanh',  'imgs/t082.jpg',  'KE010', 'NV032', 'NH008'),
('T083',  N'Carbimazole',              N'5mg',    N'Viên nén',      3400.0,  'viên',  N'Dược Phương Nam',    N'Đang Kinh Doanh',  'imgs/t083.jpg',  'KE011', 'NV033', 'NH008'),
('T084',  N'Isotretinoin',             N'10mg',   N'Viên nang',     25000.0, 'viên',  N'Dược Hoàn Mỹ',       N'Ngừng Kinh Doanh', 'imgs/t084.jpg',  'KE012', 'NV034', 'NH009'),
('T085',  N'Tretinoin',                N'0.05%',  N'Kem bôi',       39000.0, 'tuýp',  N'Dược Hoàn Mỹ',       N'Đang Kinh Doanh',  'imgs/t085.jpg',  'KE001', 'NV035', 'NH009'),
('T086',  N'Mupirocin',                N'2%',     N'Kem bôi',       45000.0, 'tuýp',  N'Dược Hoàn Mỹ',       N'Đang Kinh Doanh',  'imgs/t086.jpg',  'KE002', 'NV036', 'NH009'),
('T087',  N'Epinephrine',              N'1mg/ml', N'Ống tiêm',      52000.0, 'ống',   N'Dược Trung Ương',    N'Ngừng Kinh Doanh', 'imgs/t087.jpg',  'KE003', 'NV037', 'NH001'),
('T088',  N'Adrenaline mist',          N'0.1%',   N'Dung dịch',     38000.0, 'chai',  N'Dược Trung Ương',    N'Đang Kinh Doanh',  'imgs/t088.jpg',  'KE004', 'NV038', 'NH005'),
('T089',  N'Heparin',                  N'5000IU', N'Lọ tiêm',       87000.0, 'lọ',    N'Dược Trung Ương',    N'Đang Kinh Doanh',  'imgs/t089.jpg',  'KE005', 'NV039', 'NH007'),
('T090',  N'Enoxaparin',               N'40mg',   N'Bơm tiêm',      115000.0,'bơm',   N'Dược Trung Ương',    N'Ngừng Kinh Doanh', 'imgs/t090.jpg',  'KE006', 'NV040', 'NH007'),

('T091',  N'Warfarin',                 N'5mg',    N'Viên nén',      4200.0,  'viên',  N'Dược Đông Á',        N'Đang Kinh Doanh',  'imgs/t091.jpg',  'KE007', 'NV041', 'NH007'),
('T092',  N'Apixaban',                 N'5mg',    N'Viên nén',      23000.0, 'viên',  N'Dược Đông Á',        N'Đang Kinh Doanh',  'imgs/t092.jpg',  'KE008', 'NV042', 'NH007'),
('T093',  N'Rivaroxaban',              N'10mg',   N'Viên nén',      21000.0, 'viên',  N'Dược Đông Á',        N'Ngừng Kinh Doanh', 'imgs/t093.jpg',  'KE009', 'NV043', 'NH007'),
('T094',  N'Clindamycin',              N'300mg',  N'Viên nang',     6200.0,  'viên',  N'Dược Đại Việt',      N'Đang Kinh Doanh',  'imgs/t094.jpg',  'KE010', 'NV044', 'NH002'),
('T095',  N'Co-trimoxazole',           N'480mg',  N'Viên nén',      2400.0,  'viên',  N'Dược Đại Việt',      N'Đang Kinh Doanh',  'imgs/t095.jpg',  'KE011', 'NV045', 'NH002'),
('T096',  N'Piperacillin/Tazobactam',  N'4.5g',   N'Lọ bột tiêm',   78000.0, 'lọ',    N'Dược Đại Việt',      N'Ngừng Kinh Doanh', 'imgs/t096.jpg',  'KE012', 'NV046', 'NH002'),
('T097',  N'Doxycycline',              N'100mg',  N'Viên nang',     4100.0,  'viên',  N'Dược Đại Việt',      N'Đang Kinh Doanh',  'imgs/t097.jpg',  'KE001', 'NV047', 'NH002'),
('T098',  N'Linearin (Giả lập)',       N'50mg',   N'Viên nén',      5600.0,  'viên',  N'Dược Demo',          N'Đang Kinh Doanh',  'imgs/t098.jpg',  'KE002', 'NV048', 'NH001'),
('T099',  N'Zinc sulfate',             N'20mg',   N'Viên nén',      1500.0,  'viên',  N'Dược Dinh Dưỡng',    N'Ngừng Kinh Doanh', 'imgs/t099.jpg',  'KE003', 'NV049', 'NH004'),
('T100',  N'ORS',                      N'...',    N'Gói bột',       1200.0,  'gói',   N'Dược Dinh Dưỡng',    N'Đang Kinh Doanh',  'imgs/t100.jpg',  'KE004', 'NV050', 'NH006');
INSERT INTO LoThuoc (maLoThuoc, soLo, ngaySanXuat, ngayHetHan, giaNhap, soLuongTon, maThuoc) VALUES
-- Paracetamol (T001)
('LT001', 'SL2501A', '2024-01-15', '2026-01-14', 1200.0, 500, 'T001'),
('LT002', 'SL2506B', '2024-06-10', '2026-06-09', 1250.0, 320, 'T001'),
-- Amoxicillin (T002)
('LT003', 'SL2502C', '2024-02-05', '2026-02-04', 2900.0, 400, 'T002'),
('LT004', 'SL2510D', '2024-10-01', '2026-09-30', 2950.0, 250, 'T002'),
-- Azithromycin (T003)
('LT005', 'SL2503E', '2024-03-08', '2026-03-07', 4800.0, 220, 'T003'),
-- Ibuprofen (T004)
('LT006', 'SL2504F', '2024-04-12', '2026-04-11', 2000.0, 450, 'T004'),
('LT007', 'SL2511G', '2024-11-03', '2026-11-02', 2050.0, 180, 'T004'),
-- Naproxen (T005)
('LT008', 'SL2505H', '2024-05-01', '2026-04-30', 2600.0, 260, 'T005'),
-- Acetylcystein (T006)
('LT009', 'SL2506I', '2024-06-18', '2026-06-17', 6100.0, 180, 'T006'),
-- Loratadine (T007)
('LT010', 'SL2507J', '2024-07-09', '2026-07-08', 2800.0, 320, 'T007'),
-- Cetirizine (T008)
('LT011', 'SL2508K', '2024-08-20', '2026-08-19', 2700.0, 200, 'T008'),
-- Prednisolone (T009)
('LT012', 'SL2509L', '2024-09-14', '2026-09-13', 1500.0, 190, 'T009'),
-- Dexamethasone (T010)
('LT013', 'SL2510M', '2024-10-05', '2026-10-04', 1400.0, 210, 'T010'),
-- Omeprazole (T011)
('LT014', 'SL2511N', '2024-11-07', '2026-11-06', 3000.0, 340, 'T011'),
-- Pantoprazole (T012)
('LT015', 'SL2512O', '2024-12-01', '2026-11-30', 4200.0, 200, 'T012'),
-- Metformin (T013)
('LT016', 'SL2501P', '2024-01-22', '2026-01-21', 1800.0, 600, 'T013'),
-- Gliclazide (T014)
('LT017', 'SL2502Q', '2024-02-16', '2026-02-15', 2600.0, 280, 'T014'),
-- Amlodipine (T015)
('LT018', 'SL2503R', '2024-03-11', '2026-03-10', 2300.0, 350, 'T015'),
-- Losartan (T016)
('LT019', 'SL2504S', '2024-04-09', '2026-04-08', 3100.0, 300, 'T016'),
-- Perindopril (T017)
('LT020', 'SL2505T', '2024-05-20', '2026-05-19', 3300.0, 240, 'T017'),
-- Atorvastatin (T018)
('LT021', 'SL2506U', '2024-06-25', '2026-06-24', 5200.0, 260, 'T018'),
-- Simvastatin (T019)
('LT022', 'SL2507V', '2024-07-18', '2026-07-17', 2400.0, 280, 'T019'),
-- Clopidogrel (T020)
('LT023', 'SL2508W', '2024-08-02', '2026-08-01', 7300.0, 180, 'T020'),
('LT024', 'SL2512X', '2024-12-15', '2026-12-14', 7400.0, 150, 'T020'),
-- Vitamin C (T025)
('LT025', 'SL2509Y', '2024-09-06', '2026-09-05', 4300.0, 500, 'T025'),
-- Vitamin B1 (T026)
('LT026', 'SL2510Z', '2024-10-10', '2026-10-09', 600.0, 700, 'T026'),
-- Magnesium B6 (T027)
('LT027', 'SL2511A', '2024-11-12', '2026-11-11', 3000.0, 260, 'T027'),
-- Kali clorid (T028)
('LT028', 'SL2512B', '2024-12-20', '2026-12-19', 2400.0, 220, 'T028'),
-- Domperidone (T029)
('LT029', 'SL2501C', '2024-01-28', '2026-01-27', 1900.0, 310, 'T029'),
-- Esomeprazole (T030)
('LT030', 'SL2502D', '2024-02-22', '2026-02-21', 4200.0, 260, 'T030'),
-- Ciprofloxacin (T033)
('LT031', 'SL2503E', '2024-03-25', '2026-03-24', 5800.0, 210, 'T033'),
-- Clotrimazole (T049)
('LT032', 'SL2504F', '2024-04-14', '2026-04-13', 12000.0, 140, 'T049'),
-- ORS (T100)
('LT033', 'SL2505G', '2024-05-05', '2026-05-04', 800.0, 900, 'T100'),
-- Zinc sulfate (T099)
('LT034', 'SL2506H', '2024-06-09', '2026-06-08', 900.0, 650, 'T099'),
-- Insulin R (T021)
('LT035', 'SL2507I', '2024-07-03', '2025-07-02', 82000.0, 90, 'T021'),
-- Salbutamol (T023)
('LT036', 'SL2508J', '2024-08-11', '2026-08-10', 36000.0, 130, 'T023'),
-- Rabeprazole (T047)
('LT037', 'SL2509K', '2024-09-19', '2026-09-18', 4200.0, 170, 'T047'),
-- Ketoconazole (T050)
('LT038', 'SL2510L', '2024-10-21', '2026-10-20', 15000.0, 120, 'T050'),
-- Doxycycline (T097)
('LT039', 'SL2511M', '2024-11-25', '2026-11-24', 3000.0, 230, 'T097'),
-- Clindamycin (T094)
('LT040', 'SL2512N', '2024-12-28', '2026-12-27', 5000.0, 210, 'T094');
INSERT INTO KhuyenMai (maKhuyenMai, tenChuongTrinh, tuNgay, denNgay, phanTramGiamGia, dieuKienApDung) VALUES
('KM001', N'Khai trương rộn ràng',      '2025-01-05', '2025-01-20', 0.10, 200000),
('KM002', N'Mua nhiều giảm nhiều',      '2025-02-01', '2025-02-28', 0.08, 300000),
('KM003', N'Cuối tuần vui vẻ',          '2025-03-01', '2025-03-31', 0.05, 150000),
('KM004', N'Chăm sóc mùa dịch',         '2025-04-10', '2025-04-25', 0.12, 250000),
('KM005', N'Ngày vàng giá sốc',         '2025-05-15', '2025-05-17', 0.15, 100000),
('KM006', N'Ưu đãi hội viên Bạc',       '2025-06-01', '2025-06-30', 0.07, 100000),
('KM007', N'Ưu đãi hội viên Vàng',      '2025-06-01', '2025-06-30', 0.10, 100000),
('KM008', N'Ưu đãi hội viên Kim cương', '2025-06-01', '2025-06-30', 0.15, 100000),
('KM009', N'Back to school',            '2025-08-15', '2025-09-15', 0.06, 180000),
('KM010', N'Tri ân khách hàng',          '2025-10-01', '2025-10-31', 0.09, 200000),
('KM011', N'Ngày 11.11 săn sale',       '2025-11-10', '2025-11-12', 0.20, 150000),
('KM012', N'Cuối năm rộn ràng',         '2025-12-15', '2025-12-31', 0.12, 250000);

insert into NhaCungCap values
('NCC001',N'Long Châu','0913343123','tphcm'),
('NCC002',N'Minh Châu','0913343456','tphcm'),
('NCC003',N'Pharmacity','0911343123','tphcm')


insert into PhieuNhap (maPhieuNhap, ngayNhap, maNhaCungCap, maNhanVien)values
('PN001','2025-8-10','NCC001','NV001'),
('PN002','2025-8-10','NCC002','NV002')
insert into ChiTietPhieuNhap values
(50,'132000','PN001','LT01'),
(80,'142000','PN002','LT02')

