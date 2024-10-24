package application;

import java.util.Date;

import javax.swing.JOptionPane;

import model.dao.DAOFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {
        DepartmentDao dept = DAOFactory.createDepartmentDao();
        SellerDao sell = DAOFactory.createSellerDao();

        int opcao = 0;

        while (opcao != 3) {
            String menu = "Menu:\n"
                        + "1. Operações com Departamento\n"
                        + "2. Operações com Vendedor\n"
                        + "3. Sair";
            opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcao) {
                case 1:
                    menuDepartamento(dept);
                    break;

                case 2:
                    menuVendedor(sell, dept);
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Saindo do programa.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void menuDepartamento(DepartmentDao dept) {
        int opcaoDepartamento = 0;
        while (opcaoDepartamento != 6) {
            String menuDepartamento = "Operações com Departamento:\n"
                                    + "1. Listar todos os departamentos\n"
                                    + "2. Buscar departamento por ID\n"
                                    + "3. Inserir novo departamento\n"
                                    + "4. Atualizar departamento\n"
                                    + "5. Deletar departamento\n"
                                    + "6. Voltar ao menu principal";
            opcaoDepartamento = Integer.parseInt(JOptionPane.showInputDialog(menuDepartamento));

            switch (opcaoDepartamento) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Lista de todos os departamentos: " + dept.findAll());
                    break;

                case 2:
                    int deptId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do departamento:"));
                    Department depto = dept.findByID(deptId);
                    JOptionPane.showMessageDialog(null, "Departamento encontrado: " + depto);
                    break;

                case 3:
                    String deptName = JOptionPane.showInputDialog("Informe o nome do novo departamento:");
                    Department novoDepto = new Department(null, deptName);
                    dept.inserir(novoDepto);
                    JOptionPane.showMessageDialog(null, "Departamento inserido com sucesso!");
                    break;

                case 4:
                    int updateDeptId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do departamento para atualizar:"));
                    String updateDeptName = JOptionPane.showInputDialog("Informe o novo nome do departamento:");
                    Department updatedDepto = new Department(updateDeptId, updateDeptName);
                    dept.update(updatedDepto);
                    JOptionPane.showMessageDialog(null, "Departamento atualizado com sucesso!");
                    break;

                case 5:
                    int deleteDeptId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do departamento a ser deletado:"));
                    dept.deleteById(deleteDeptId);
                    JOptionPane.showMessageDialog(null, "Departamento deletado com sucesso!");
                    break;

                case 6:
                    // Voltar ao menu principal
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void menuVendedor(SellerDao sell, DepartmentDao dept) {
        int opcaoVendedor = 0;
        while (opcaoVendedor != 6) {
            String menuVendedor = "Operações com Vendedor:\n"
                                + "1. Listar todos os vendedores\n"
                                + "2. Buscar vendedor por ID\n"
                                + "3. Inserir novo vendedor\n"
                                + "4. Atualizar vendedor\n"
                                + "5. Deletar vendedor\n"
                                + "6. Voltar ao menu principal";
            opcaoVendedor = Integer.parseInt(JOptionPane.showInputDialog(menuVendedor));

            switch (opcaoVendedor) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Lista de todos os vendedores: " + sell.findAll());
                    break;

                case 2:
                    int sellerId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do vendedor:"));
                    Seller seller = sell.findByID(sellerId);
                    JOptionPane.showMessageDialog(null, "Vendedor encontrado: " + seller);
                    break;

                case 3:
                    String sellerName = JOptionPane.showInputDialog("Informe o nome do vendedor:");
                    String sellerEmail = JOptionPane.showInputDialog("Informe o e-mail do vendedor:");
                    double salary = Double.parseDouble(JOptionPane.showInputDialog("Informe o salário do vendedor:"));
                    int deptId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do departamento:"));
                    Department depto = dept.findByID(deptId);

                    Seller novoSeller = new Seller(null, sellerName, sellerEmail, new Date(), salary, depto);
                    sell.inserir(novoSeller);
                    JOptionPane.showMessageDialog(null, "Vendedor inserido com sucesso!");
                    break;

                case 4:
                    int updateSellerId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do vendedor para atualizar:"));
                    String updateSellerName = JOptionPane.showInputDialog("Informe o novo nome do vendedor:");
                    String updateSellerEmail = JOptionPane.showInputDialog("Informe o novo e-mail do vendedor:");
                    double updateSalary = Double.parseDouble(JOptionPane.showInputDialog("Informe o novo salário do vendedor:"));
                    int updateDeptId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do departamento do vendedor:"));
                    Department updateDepto = dept.findByID(updateDeptId);

                    Seller updatedSeller = new Seller(updateSellerId, updateSellerName, updateSellerEmail, new Date(), updateSalary, updateDepto);
                    sell.update(updatedSeller);
                    JOptionPane.showMessageDialog(null, "Vendedor atualizado com sucesso!");
                    break;

                case 5:
                    int deleteSellerId = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID do vendedor a ser deletado:"));
                    sell.deleteById(deleteSellerId);
                    JOptionPane.showMessageDialog(null, "Vendedor deletado com sucesso!");
                    break;

                case 6:
                    // Voltar ao menu principal
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
