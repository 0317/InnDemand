package demand.inn.com.inndemand;

/**
 * Created by akash on 11/8/16.
 */
public class CallClass {

    /*
                call_hotel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ActivityCompat.checkSelfPermission(DashBoard.this,
                                    android.Manifest.permission.CALL_PHONE) !=
                                    PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions,
                                // and then overriding
                                //   public void onRequestPermissionsResult
                                // (int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the
                                // permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale
                                        (DashBoard.this, android.Manifest.permission.CALL_PHONE)) {

                                    // Show an expanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the user's response! After the user
                                    // sees the explanation, try again to request the permission.

                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(DashBoard.this);
                                    builder.setMessage("Need to Call")
                                            .setPositiveButton("Call",
                                                    new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                }
                                            })
                                            .setNegativeButton("Cancel",
                                                    new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog dialog = builder.create();
                                    dialog.setTitle("Permissions");
                                    dialog.show();

                                } else {

                                    // No explanation needed, we can request the permission.

//                                    ActivityCompat.requestPermissions(HotelDetails.this,
//                                            new String[]{Manifest.permission.CALL_PHONE},
//                                            MY_PERMISSIONS_REQUEST_CALL);

                                    // MY_PERMISSIONS_REQUEST_CALL is an
                                    // app-defined int constant. The callback method gets the
                                    // result of the request.
                                }



                                return;
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }*/

}
