Drupal.behaviors.embedquicktime = function(context) {
  if (Drupal.settings.eqt_jquery instanceof Array) {
    Drupal.settings.eqt_jquery = Drupal.settings.eqt_jquery[0];
  }
  if (Drupal.settings.eqt_plugin instanceof Array) {
    Drupal.settings.eqt_plugin = Drupal.settings.eqt_plugin[0];
  }  
  $.embedquicktime({jquery: Drupal.settings.eqt_jquery, plugin: Drupal.settings.eqt_plugin});
}