package com.kramer.a18882652.citybiolder;

    public class Commercial implements Structure
    {
        int imageId;
        public Commercial()
        {
        }
        public Commercial(int imageId)
        {
            this.imageId = imageId;
        }

        public int getImageID()
        {
            return imageId;
        }
        @Override
        public void setImageID(int imageID) {
            this.imageId = imageID;
        }

        @Override
        public int getCost() {
            return Integer.valueOf("" +SettingsModel.getInstance().getData(GameDataSchema.SettingsTable.Cols.COMM_BUILDING_COST));
        }

    }
