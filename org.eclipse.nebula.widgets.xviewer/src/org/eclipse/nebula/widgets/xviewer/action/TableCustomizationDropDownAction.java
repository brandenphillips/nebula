package org.eclipse.nebula.widgets.xviewer.action;

import java.util.logging.Level;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.customize.CustomizeData;
import org.eclipse.nebula.widgets.xviewer.util.internal.XViewerLog;
import org.eclipse.nebula.widgets.xviewer.util.internal.images.XViewerImageCache;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class TableCustomizationDropDownAction extends Action implements IMenuCreator {
   private Menu fMenu;
   private final XViewer xViewer;

   public TableCustomizationDropDownAction(XViewer xViewer) {
      this.xViewer = xViewer;
      setText("Table Customization");
      setMenuCreator(this);
   }

   @Override
   public ImageDescriptor getImageDescriptor() {
      return XViewerImageCache.getImageDescriptor("customize.gif");
   }

   @Override
   public void run() {
      xViewer.getCustomizeMgr().handleTableCustomization();
   }

   @Override
   public String getToolTipText() {
      return "Customize Table";
   }

   public Menu getMenu(Control parent) {
      if (fMenu != null) fMenu.dispose();

      fMenu = new Menu(parent);

      addActionToMenu(fMenu, new TableCustomizationAction(xViewer));
      addActionToMenu(fMenu, new TableCustomizationCustomizeDataAction(xViewer,
            xViewer.getCustomizeMgr().getTableDefaultCustData()));
      new MenuItem(fMenu, SWT.SEPARATOR);
      try {
         for (CustomizeData custData : xViewer.getCustomizeMgr().getSavedCustDatas()) {
            addActionToMenu(fMenu, new TableCustomizationCustomizeDataAction(xViewer, custData));
         }
      } catch (Exception ex) {
         XViewerLog.log(TableCustomizationDropDownAction.class, Level.SEVERE, ex);
      }
      return fMenu;
   }

   public void dispose() {
      if (fMenu != null) {
         fMenu.dispose();
         fMenu = null;
      }
   }

   public Menu getMenu(Menu parent) {
      return null;
   }

   protected void addActionToMenu(Menu parent, Action action) {
      ActionContributionItem item = new ActionContributionItem(action);
      item.fill(parent, -1);
   }

   /**
    * Get's rid of the menu, because the menu hangs on to * the searches, etc.
    */
   void clear() {
      dispose();
   }

}
