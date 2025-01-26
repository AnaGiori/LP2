SRC_DIR = src/main/java
BIN_DIR = bin

compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SRC_DIR)/conta/Conta.java \
		$(SRC_DIR)/conta/ContaCorrente.java \
		$(SRC_DIR)/conta/ContaPoupanca.java \
		$(SRC_DIR)/conta/ContaSalario.java \
		$(SRC_DIR)/agencia/Agencia.java \
		$(SRC_DIR)/persistencia/GerenciadorPersistencia.java \
		$(SRC_DIR)/excecoes/*.java \
		$(SRC_DIR)/cliente/Cliente.java \
		$(SRC_DIR)/Main.java

run:
	java -cp $(BIN_DIR) Main

clean:
	rm -rf $(BIN_DIR)