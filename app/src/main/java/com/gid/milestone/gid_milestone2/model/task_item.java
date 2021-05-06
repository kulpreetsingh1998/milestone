package com.gid.milestone.gid_milestone2.model;

public class task_item {

        String time,Title, Description;

        public task_item() {
        }

        public task_item(String Title,String Description,String time) {
            this.Title = Title;
            this.Description = Description;
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time= time;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }
    }

