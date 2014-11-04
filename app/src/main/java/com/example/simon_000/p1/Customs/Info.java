package com.example.simon_000.p1.Customs;

/**
 * Created by simon_000 on 2014-09-26.
 */
public class Info {
    private String _title;
   private String _desc;
    private String _cate;
    private Double _value;
   private  String _date;
     private int _id;

    public Info() {

    }
        public Info(String date, String title, Double Value, String details, String cate) {
            this._date = date;
            this._title = title;
            this._value = Value;
            this._desc = details;
            this._cate = cate;

        }
        public Info(int id, String date, String title, Double Value, String details, String cate) {
            this._date = date;
            this._title = title;
            this._value = Value;
            this._desc = details;
            this._cate = cate;
            this._id = id;
        }

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String get_title() {
            return _title;
        }

        public void set_title(String _title) {
            this._title = _title;
        }

        public String get_desc() {
            return _desc;
        }

        public void set_desc(String _desc) {
            this._desc = _desc;
        }

        public String get_cate() {
            return _cate;
        }

        public void set_cate(String _cate) {
            this._cate = _cate;
        }

        public Double get_value() {
            return _value;
        }

        public void set_value(Double _value) {
            this._value = _value;
        }

        public String get_date() {
            return _date;
        }

        public void set_date(String _date) {
            this._date = _date;
        }

        public String toString (){
            return _title;
        }





}
