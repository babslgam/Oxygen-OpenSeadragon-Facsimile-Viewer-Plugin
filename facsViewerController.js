FacsViewerController = function () {

        this.facsCount = 0;
        this.facsIndex = {
                imageServerUrl: "",
                facsIndexElements: []
        };
        self = this;

        elementSelectorListener = {
                handleEvent(e) {
                        domEventType = e.getType();
                        if (domEventType.equals("change")) {
                                elementName = e.target.value;
                                attributeName = pluginController.currentProjectSettings.imageReferences[elementName].attributeName;
                                generateFacsIndex(elementName, attributeName);
                                sel = document.getElementById("imageselector");

                                for (i = 0; i < self.facsCount; i++) {
                                        option = document.createElement("option");
                                        val = self.facsIndex.facsIndexElements[i].imageName;
                                        option.value = val;
                                        option.innerHTML = val;
                                        sel.appendChild(option);
                                }
                                sel.addEventListener("change", facsSelectorListener, false);
                                webengine.executeScript("initViewerNavigation()");

                        }
                }
        }


        pluginController.projectChangedEvent = function() {
                imageRefs = JSON.stringify(pluginController.currentProjectSettings.imageReferences);
                pluginController.guiUtils.executeOnJAVAFXThread(
                        'webengine.executeScript(\'updateTableAndSelectors(' + imageRefs + ');\'); \
                                document = webengine.getDocument(); \
                                elementselector = document.getElementById("elementselector"); \
                                elementselector.addEventListener("change",elementSelectorListener,false); \
                                '
                );
        };

        caretListenerAuthorMode = {
                caretMoved: function (caretEvent) {
                        if (self.facsIndex.facsIndexElements.length > 0) {
                                prevFacsElementImageName = findImage(pluginController.currentPage);
                        }
                }
        }
        caretListenerTextMode = {
                caretUpdate: function (caretEvent) {
                        if (self.facsIndex.facsIndexElements.length > 0) {
                                prevFacsElementImageName = findImage(pluginController.currentPage);
                                pluginController.guiUtils.executeOnJAVAFXThread('webengine.executeScript("createViewerOrLoadImage(\'' + pluginController.currentProjectSettings.imageReferences[elementselector.value].imageServerUrl + '\',\'' + prevFacsElementImageName + '\');updatePageSelector(\''+prevFacsElementImageName+ '\')");')
                        }
                }
        }
        pluginController.caretListeners.caretListenerAuthorMode = caretListenerAuthorMode;
        pluginController.caretListeners.caretListenerTextMode = caretListenerTextMode;

        facsSelectorListener =
                {
                        handleEvent(e) {
                                domEventType = e.getType();
                                if (domEventType.equals("change")) {
                                        Packages.java.lang.System.out.print("facsSelector used" + Packages.java.lang.System.lineSeparator());
                                        elementselector = document.getElementById("elementselector");
                                        pluginController.guiUtils.executeOnJAVAFXThread(
                                                'webengine.executeScript("createViewerOrLoadImage(\'' + pluginController.currentProjectSettings.imageReferences[elementselector.value].imageServerUrl + '\',\'' + e.target.value + '\');");'
                                        )
                                        pluginController.guiUtils.executeOnJAVAFXThread('webengine.executeScript("updateViewerNavigation()")'); 
                                        facsElementOffset = facsViewerController.facsIndex.facsIndexElements[e.target.selectedIndex].endoffset;
                                        pluginController.currentPage.setCaretPosition(facsElementOffset);
                                        pluginController.currentPage.scrollCaretToVisible();

                                }
                        }
                }

         function generateFacsIndex(elementName, attributeName) {
                self.facsIndex.facsIndexElements = [];
                if (pluginController.currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage) {

                        authorAccess = pluginController.currentPage.getAuthorAccess();
                        authorDocumentController = authorAccess.getDocumentController();
                        authorFacsNodes = authorDocumentController.findNodesByXPath("//" + elementName, true, true, true);
                        for (i = 0; i < authorFacsNodes.length; i++) {
                                facsIndexElement = {};
                                if (authorFacsNodes[i] instanceof Packages.ro.sync.ecss.extensions.api.node.AuthorElement) {
                                        authorElement = authorFacsNodes[i];
                                        facsIndexElement.imageName = authorElement.getAttribute(pluginController.currentProjectSettings.facsAttributeName).getValue();
                                        facsIndexElement.startoffset = authorFacsNodes[i].getStartOffset();
                                        facsIndexElement.endoffset = authorFacsNodes[i].getEndOffset();
                                        self.facsIndex.facsIndexElements.push(facsIndexElement);
                                }
                        }


                } else if (pluginController.currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextEditorPage) {

                        facsElements = pluginController.currentPage.evaluateXPath("//" + elementName);
                        facsElementsRanges = pluginController.currentPage.findElementsByXPath("//" + elementName);
                        for (i = 0; i < facsElements.length; i++) {
                                facsIndexElement = {};
                                facsIndexElement.imageName = facsElements[i].getAttribute(attributeName);
                                facsIndexElement.startoffset = pluginController.currentPage.getOffsetOfLineStart(facsElementsRanges[i].getStartLine()) + facsElementsRanges[i].getStartColumn() - 1;
                                facsIndexElement.endoffset = pluginController.currentPage.getOffsetOfLineEnd(facsElementsRanges[i].getEndLine()) + facsElementsRanges[i].getEndColumn() - 1;
                                self.facsIndex.facsIndexElements.push(facsIndexElement);
                        }
                }

                self.facsCount = self.facsIndex.facsIndexElements.length;
        }

        function findImage(textPageOrAuthorAccess) {
                caretOffset = 0;
                imgName = self.facsIndex.facsIndexElements[0].imageName;
                if (textPageOrAuthorAccess.hasSelection()) {
                        caretOffset = textPageOrAuthorAccess.getSelectionStart();
                } else {
                        caretOffset = textPageOrAuthorAccess.getCaretOffset();
                }
                i = 0;
                do {
                        imgName = self.facsIndex.facsIndexElements[i].imageName;
                        i++;
                        Packages.java.lang.System.out.print("iterator: " + i + Packages.java.lang.System.lineSeparator());

                } while (i < self.facsCount && self.facsIndex.facsIndexElements[i].startoffset < caretOffset);
                Packages.java.lang.System.out.print(caretOffset);
                Packages.java.lang.System.out.print(imgName);
                return imgName;

        }

}