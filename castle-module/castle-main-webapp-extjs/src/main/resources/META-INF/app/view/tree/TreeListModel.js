Ext.define('app.view.tree.TreeListModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.tree-list',

    formulas: {
        selectionText: function(get) {
            var selection = get('treelist.selection'),
                path;
            if (selection) {
                path = selection.getPath('text');
                path = path.replace(/^\/Root/, '');
                return 'Selected: ' + path;
            } else {
                return 'No node selected';
            }
        }
    },

    stores: {
        navItems: {
            type: 'tree',
            root: {
                expanded: true,
                children: [{
                    text: 'Home',
                    iconCls: 'fa fa-home',
                    children: [{
                        text: 'Messages',
                        iconCls: 'fa fa-inbox',
                        leaf: true
                    },{
                        text: 'Archive',
                        iconCls: 'fa fa-database',
                        children: [{
                            text: 'First',
                            iconCls: 'fa fa-sliders',
                            leaf: true
                        },{
                            text: 'No Icon',
                            iconCls: null,
                            leaf: true
                        }]
                    },{
                        text: 'Music',
                        iconCls: 'fa fa-music',
                        leaf: true
                    },{
                        text: 'Video',
                        iconCls: 'fa fa-film',
                        leaf: true
                    }]
                },{
                    text: 'Users',
                    iconCls: 'fa fa-user',
                    children: [{
                        text: 'Tagged',
                        iconCls: 'fa fa-tag',
                        leaf: true
                    },{
                        text: 'Inactive',
                        iconCls: 'fa fa-trash',
                        leaf: true
                    }]
                },{
                    text: 'Groups',
                    iconCls: 'fa fa-group',
                    leaf: true
                },{
                    text: 'Settings',
                    iconCls: 'fa fa-wrench',
                    children: [{
                        text: 'Sharing',
                        iconCls: 'fa fa-share-alt',
                        leaf: true
                    },{
                        text: 'Notifications',
                        iconCls: 'fa fa-flag',
                        leaf: true
                    },{
                        text: 'Network',
                        iconCls: 'fa fa-signal',
                        leaf: true
                    }]
                }]
            }
        }
    }
});

