package zara.zio.turn.domain;

public class MaterialVO {
	
   private int material_code; // 준비물코드 
   private String material_type_code; // 준비물타입 
   private String material_name; // 준비물이름
   private int manager_check; // 관리자확인 
   private String user_id; // 유저아이디 
   private int group_Code; // 그룹코드 
   private int material_check; // 준비물체크
   
   public int getMaterial_code() {
      return material_code;
   }
   public void setMaterial_code(int material_code) {
      this.material_code = material_code;
   }
   public String getMaterial_type_code() {
      return material_type_code;
   }
   public void setMaterial_type_code(String material_type_code) {
      this.material_type_code = material_type_code;
   }
   public String getMaterial_name() {
      return material_name;
   }
   public void setMaterial_name(String material_name) {
      this.material_name = material_name;
   }
   public int getManager_check() {
      return manager_check;
   }
   public void setManager_check(int manager_check) {
      this.manager_check = manager_check;
   }
   public String getUser_id() {
      return user_id;
   }
   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }
   public int getGroup_Code() {
      return group_Code;
   }
   public void setGroup_Code(int group_Code) {
      this.group_Code = group_Code;
   }
   public int getMaterial_check() {
      return material_check;
   }
   public void setMaterial_check(int material_check) {
      this.material_check = material_check;
   }
   
   

}