# Nome do compilador
JAVAC = javac

# Diretório de saída para os arquivos compilados
OUT_DIR = bin

# Diretório de origem dos arquivos .java
SRC_DIR = .

# Nome do arquivo principal (classe com o método main)
MAIN_CLASS = Main

# Lista de arquivos-fonte
SOURCES = $(SRC_DIR)/Cliente.java $(SRC_DIR)/Conta.java $(SRC_DIR)/Main.java

# Lista de arquivos compilados
CLASSES = $(OUT_DIR)/Cliente.class $(OUT_DIR)/Conta.class $(OUT_DIR)/Main.class

# Comando padrão (compila e executa)
.PHONY: all
all: compile run

# Compilação
compile: $(CLASSES)

$(CLASSES): $(SOURCES)
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SOURCES)

# Execução
run:
	java -cp $(OUT_DIR) $(MAIN_CLASS)

# Limpeza (remove arquivos compilados)
.PHONY: clean
clean:
	rm -rf $(OUT_DIR)

